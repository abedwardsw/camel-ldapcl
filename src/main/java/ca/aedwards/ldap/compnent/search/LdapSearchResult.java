package ca.aedwards.ldap.compnent.search;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;


public class LdapSearchResult {

	private Attributes attributes;
	private Name dn;
	
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	public void setDn(Name dn) {
		this.dn = dn;
	}

	public String toString() {
		try {
		return String.format("dn: %s attrs: targetDn: %s chTime: %s chNum: %s chType: %s newRdn: %s delOldRdn: %s newSup: %s"
				, dn, getTargetDn(), getChangeTime(), getChangeNumber(), getChangeType(), getNewRdn(), getDeleteOldRdn(), getNewSuperior());
		} catch (NamingException e) {
			System.out.println("Caught naming exception: " + e);
			return "null";
		}
	}
	
	public String getTargetDn() throws NamingException {
		return getAttr("targetDn");
	}
	public String getChangeTime() throws NamingException {
		return getAttr("changeTime");
	}
	public String getChangeNumber() throws NamingException {
		return getAttr("changeNumber");
	}
	public String getChangeType() throws NamingException {
		return getAttr("changeType");
	}
	public String getNewRdn() throws NamingException {
		return getAttr("newRdn");
	}
	public String getDeleteOldRdn() throws NamingException {
		return getAttr("deleteOldRdn");
	}
	public String getNewSuperior() throws NamingException {
		return getAttr("newSuperior");
	}
	public String getChanges() {
		return getAttr("changes");
	}
	public String getAttr(String attr) {
		if (attributes.get(attr) == null) {
			return null;
		}
		try {
		return attributes.get(attr).get().toString();
		} catch (NamingException e) { 
			return null; 
		}
		
	}
	public Attributes getAttributes() {
		return attributes;
	}
	
}
