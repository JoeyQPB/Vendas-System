package br.com.joey;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({SellDAOTest.class, ClienteDAOTeste.class, ClienteServiceTeste.class, ProductDAOTest.class, ProductServiceTeste.class })
public class AllTests {}
