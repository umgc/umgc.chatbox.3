/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.chatbot.permit.municipal.db.ProcessRequest;
import static org.junit.Assert.*;

/**
 *
 * @author bmurray
 */
public class ProcessRequestJUnitTest {

  public ProcessRequestJUnitTest() {}

  @BeforeClass
  public static void setUpClass() {}

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  // @Test
  // public void hello() {}

  @Test
  public void retrievePermitInfo() throws Exception {
    assertEquals(new ProcessRequest("localhost", "root", "5mLQ9F!mZeGxI1!JBOi2p&wXcgn3O3*6").retrieveRegulationInfo("ECSP-CG-1", "Fencing"),
        "https://www.cityofpasadena.net/wp-content/uploads/sites/30/Zoning-Permit-Application.pdf?v=1602628892503");
  }

  @Test
  public void retrieveRegulationInfoTest() throws Exception {
    assertEquals(new ProcessRequest("localhost", "root", "5mLQ9F!mZeGxI1!JBOi2p&wXcgn3O3*6").retrieveRegulationInfo("ECSP-CG-1", "Short Term Rental"),
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT17_ZONING_CODE_ART5STSPLAUS_CH17.50STSPLAUS_17.50.296SHRMRE");
  }


  @Test
  public void retrieveDevelopmentStandardsInfoTest() throws Exception {
    assertEquals(new ProcessRequest("localhost", "root", "5mLQ9F!mZeGxI1!JBOi2p&wXcgn3O3*6").retrieveDevelopmentStandardsInfo("ECSP-CG-1"),
        "General Development Standards: https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT17_ZONING_CODE_ART3SPPLST_CH17.31EACOSPPL_17.31.050ECGEDEST Additional development standards: None Garden standards: None Frontage and facades standards: None");
  }



}
