##############################################################
# Makefile for building and testing
##############################################################

# Run make SKIP_TEST=y
SKIP_TESTS:=

# Version vars
VERSION:=1.0.$(shell git rev-list HEAD | wc -l)
MUNICIPALPERMITCHABOT_JAR=MunicipalPermitChabot-1.0.0.jar

# Docker vars
APP_NAME=municipal-permit-chabot.app
APP_TAG=$(VERSION)
APP_IMG=$(APP_NAME):$(APP_TAG)
REMOTE_IMG:=docker.io/umgccaps/$(APP_IMG)
BUILD_IMG=docker.io/umgccaps/advance-development-factory:latest
BRANCH=$(shell git rev-parse --abbrev-ref HEAD)

# Maven options
MAVEN_OPTS:=-Dversion=$(VERSION)

# Unique ID used for devel Azure deployments
UUID_FILENAME:=user.uuid
UUID:=$(shell cat $(UUID_FILENAME) 2> /dev/null || (uuidgen | sed s/'-'/''/g | head -c 10 \
         | tr A-Z a-z > $(UUID_FILENAME) && cat $(UUID_FILENAME)))

# Skip test flag
# make all SKIP_TESTS=y <- doest not run unit tests
ifdef SKIP_TESTS
	MAVEN_OPTS:=$(MAVEN_OPTS) -Dmaven.test.skip=true
endif

# PHONY
.PHONY: all image start clean push sonar

##############################################################
#	make all:
#		This recipe starts the municipal-permit-chabot-build-env with repo volumed
#		mapped into the container can creates the municipal-permit-chabot-app jar
#		then exits. This is useful so that developer do not 
#		need to modify environments.
#
##############################################################
all:
	docker run -v $(PWD)/:/repo --entrypoint '/bin/bash' $(BUILD_IMG) \
		-c 'cd /repo && make target/$(MUNICIPALPERMITCHABOT_JAR) VERSION=$(VERSION)'

# This internal recipe is used by build to create the municipal-permit-chabot versioned jar
target/$(MUNICIPALPERMITCHABOT_JAR):	
	mvn $(MAVEN_OPTS) package -f pom.xml

##############################################################
#	make image:
#		This recipe create the project-tracker Docker image
#
##############################################################
image: target/$(MUNICIPALPERMITCHABOT_JAR)
	cp target/$(MUNICIPALPERMITCHABOT_JAR) ./$(MUNICIPALPERMITCHABOT_JAR)
	docker build -f ./docker/Dockerfile --build-arg VERSION=$(VERSION) \
		--build-arg MUNICIPALPERMITCHABOT_APP=$(MUNICIPALPERMITCHABOT_JAR) -t $(APP_IMG) .
	rm -rf ./$(MUNICIPALPERMITCHABOT_JAR)

sonar:
	docker run -v $(PWD)/:/repo --entrypoint '/bin/bash' $(BUILD_IMG) \
		-c 'cd /repo &&	mvn verify sonar:sonar -Dsonar.branch.name=$(BRANCH)'
		
##############################################################
#	make start:
#		This recipe start the project-tracker Docker app
#
##############################################################
start:
	docker run --rm --name $(APP_NAME) $(APP_IMG)

##############################################################
#	make clean:
#		This recipe cleans the user workspace
#
##############################################################
clean:
	@mvn $(MAVEN_OPTS) clean -f pom.xml

##############################################################
#	make push:
#		This recipe pushes the Docker vlol application to the
#		Azure container registry 
#
##############################################################
push:
	docker tag $(APP_IMG) $(REMOTE_IMG)
	docker push $(REMOTE_IMG)
