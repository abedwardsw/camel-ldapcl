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
package ca.aedwards.ldap.compnent;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.spi.Required;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Represents the component that manages {@link LdapClEndpoint}.
 */
public class LdapClComponent extends DefaultComponent {
	
	private LdapTemplate ldapTemplate;

	public LdapClComponent() {
		super();
		System.out.println("Creating jms component");
	}
	
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        LdapClEndpoint endpoint = new LdapClEndpoint(uri, this);
        endpoint.setLdapTemplate(ldapTemplate);
        endpoint.setName(remaining);
        System.out.println("LdapClComponent: starting: uri: " +uri + " remaining: " + remaining + " params:" + parameters);
        setProperties(endpoint, parameters);
        return endpoint;
    }
    
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	@Required
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}    
    
}
