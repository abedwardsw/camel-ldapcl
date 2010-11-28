/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.aedwards.ldap.component;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import ca.aedwards.ldap.compnent.LdapClComponent;


public class LdapClComponentTest extends CamelTestSupport {

    @Test
    public void testTimerInvokesBeanMethod() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(5);
        System.out.println("starting is satisfied");
        assertMockEndpointsSatisfied(60, TimeUnit.SECONDS);
        //mock.assertIsSatisfied(450000);
        System.out.println("Finished is satisfied");
        //assertMockEndpointsSatisfied();
    }

    @Before
    public void setUp() throws Exception {
    	System.out.println("setUp");
    	super.setUp();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        LdapContextSource ldapContext = new LdapContextSource();
        ldapContext.setUrl("ldap://localhost:1389");
        ldapContext.setBase("cn=changelog");
        ldapContext.setUserDn("cn=Directory Manager");
        ldapContext.setPassword("admin");
        ldapContext.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate();
        ldapTemplate.setContextSource(ldapContext);
        ldapTemplate.afterPropertiesSet();
        
        LdapClComponent ldapComponent = new LdapClComponent();
        ldapComponent.setLdapTemplate(ldapTemplate);
      
        context.addComponent("ldapcl", ldapComponent);
        System.out.println("finished add component");
        //context.addComponent("jms", context.getComponent("seda"));
        return context;
    }

    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                //from("ldapcl://foo?srv=opends")    // will send a message every 500ms
            	from("ldapcl:adam?srv=opends")    // will send a message every 500ms
                  .to("ldapcl://bar")   // prints message to stdout
                  .to("mock:result");       // to actually test that a message arrives
            }
        };
    }
}
