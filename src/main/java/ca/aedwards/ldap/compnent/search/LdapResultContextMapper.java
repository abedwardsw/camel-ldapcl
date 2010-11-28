package ca.aedwards.ldap.compnent.search;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

public class LdapResultContextMapper extends AbstractContextMapper {
    public Object doMapFromContext(DirContextOperations ctx) {
        LdapSearchResult r = new LdapSearchResult();
        
        r.setAttributes(ctx.getAttributes());
        r.setDn(ctx.getDn());
        return r;
     }

}
