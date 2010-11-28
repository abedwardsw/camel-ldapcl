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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Represents a HelloWorld endpoint.
 */
public class LdapClEndpoint extends DefaultEndpoint {
	private String serverType = "default";
	private String name;
	private boolean persist;
	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		this.persist = persist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private LdapTemplate ldapTemplate;
	
    public LdapClEndpoint() {
    }

    public LdapClEndpoint(String uri, LdapClComponent component) {
        super(uri, component);
    }

    public LdapClEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new LdapClProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new LdapClConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
    
    public void setSrv(String serverType) {
    	System.out.println("setting serverType to: " + serverType);
    	this.serverType = serverType;
    }
    public String getSrv() {
    	return serverType;
    }
    
    
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}      
}
