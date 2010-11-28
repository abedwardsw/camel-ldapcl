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

import java.util.Date;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.GreaterThanOrEqualsFilter;

import ca.aedwards.ldap.compnent.search.LdapResultContextMapper;
import ca.aedwards.ldap.compnent.search.LdapSearchResult;

/**
 * The HelloWorld consumer.
 */
public class LdapClConsumer extends ScheduledPollConsumer {
    private final LdapClEndpoint endpoint;
    private long clLast = 0;


    public LdapClConsumer(LdapClEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    // poll method will fire every 500 ms by default 
    protected void poll() throws Exception {

        //
        List<LdapSearchResult> users = this.getAllPersonNames();
        for (LdapSearchResult change : users) {
        	this.sendMessage(change);
        	this.clLast = Long.parseLong(change.getChangeNumber()) + 1;
        }

    	
    }

    private void sendMessage(LdapSearchResult result) throws Exception {

        Exchange exchange = endpoint.createExchange();
        
        
        // create a message body
        Date now = new Date();
        exchange.getIn().setBody(result.getChanges());
        
        // Set the headers/properties
        NamingEnumeration<String> names = result.getAttributes().getIDs();
        while (names.hasMore()) {
        	String key = names.next();
        	String value = result.getAttr(key);
        	exchange.getIn().setHeader("LDAPCL_" + key.toUpperCase(), value);
        }

        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }    	
    	
    }
    
    public List<LdapSearchResult> getAllPersonNames() {
    	SearchControls sc = new SearchControls();
    	sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
    	sc.setReturningObjFlag(true);
    	AndFilter filter = new AndFilter();
    	//filter.and(new EqualsFilter("objectclass", "person"));
    	filter.and(new GreaterThanOrEqualsFilter("changeNumber", Long.toString(clLast)));
    	List<LdapSearchResult> results =  endpoint.getLdapTemplate().search(
    			DistinguishedName.EMPTY_PATH
    		   ,filter.encode()
    		   , sc
    		   , new LdapResultContextMapper()
    			);
    	//System.out.println("results: " + results.toString());
    	System.out.println("Filter: " + filter.toString());
    	return results;
    }    
    
    
}
