package br.com.joey.integracaoTestes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ClienteIntegracaoTestes.class, ProductIntegracaoTestes.class })
public class IntegracaoSuite {

}
