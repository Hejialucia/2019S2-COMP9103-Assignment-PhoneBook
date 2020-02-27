package ECB19S2;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class ContactHandler {
	/**
	 * 
	 * @param restInf
	 * @return
	 */
	public Contact addContact(String restInf) {
		// separate by ";"
		String[] split = restInf.trim().split("\\;");
		Contact contact = new Contact();
		Scanner s = null;
		for (String string : split) {
			// name Jo Bloggs; birthday 08-07-1900; phone 88884444; address 9001 Chester
			// Crescent, Chatswood, NSW 2057
			s = new Scanner(string);
			String firstWord, line;
			while (s.hasNext()) {
				firstWord = s.next();
				if (s.hasNextLine()) {
					line = s.nextLine().trim();
					if (firstWord.equalsIgnoreCase("name")) {
						contact.setName(line);
					} else if (firstWord.equalsIgnoreCase("birthday")) {
						contact.setBirthday(line);
					} else if (firstWord.equalsIgnoreCase("phone")) {
						contact.setPhone(line);
					} else if (firstWord.equalsIgnoreCase("address")) {
						contact.setAddress(line);
					} else if (firstWord.equalsIgnoreCase("email")) {
						contact.setEmail(line);
					} else {
						continue;
					}
				}
			}
		}
		s.close();
		return contact;
	}

	/**
  * delete
  * 
  * @param restInf
  * @param contactList
  */
		public void delete(String restInf, List<Contact> contactList) {
  			String[] split = restInf.split(";");
  			String name = split[0].trim();
  			String birthday = split[1].trim();
  		for(int i=0;i<contactList.size();i++){
   			Contact contact =  contactList.get(i);
    			// name and birthday
    			if (null != name && null != birthday) {
     				contact.validBirthday(contact.getBirthday());
     					if (contact.getName().equals(name)
       						&& contact.getBirthday().equals(birthday)) {
      					contactList.remove(i);
     					}
    			// only name
    			} else if (null != name && null == birthday) {
     				if (contact.getName().equals(name)) {
      				contactList.remove(i);
     			}
    		}
    
   	}
 }

	/**
	 * add or modify
	 * 
	 * @param restInf
	 * @param contactList
	 */
	public void add(String restInf, List<Contact> contactList) {

		Contact addContact = this.addContact(restInf);
		if (null != addContact) {
			boolean flag = false;
			for (Contact contact : contactList) {
				if (contact.getName().equals(addContact.getName())
						&& contact.getBirthday().equals(addContact.getBirthday())) {
					update(contact, addContact);
					flag = true;
				}
			}
			if (!flag) {
				add(addContact, contactList);
			}

		}
	}

	/**
	 * 
	 * @param addContact
	 * @param contactList
	 */
	private void add(Contact addContact, List<Contact> contactList) {
		String name = addContact.getName();
		String birthday = addContact.getBirthday();
		String phone = addContact.getPhone();
		String address = addContact.getAddress();
		String email = addContact.getEmail();

		if (addContact.validName(name) && addContact.validBirthday(birthday)) {
			if (!addContact.validPhone(phone)) {
				addContact.setPhone(null);
			}
			if (!addContact.validAddress(address)) {
				addContact.setAddress(null);
			}
			if (!addContact.validEmail(email)) {
				addContact.setEmail(null);
			}
		contactList.add(addContact);	
		}
		

	}	

	public void update(Contact oldcontact, Contact newcontact) {
		String phone = newcontact.getPhone();
		String address = newcontact.getAddress();
		String email = newcontact.getEmail();
		
		if (phone != null && newcontact.validPhone(phone)) {
			oldcontact.setPhone(phone);
		}
		if (address != null && newcontact.validAddress(address)) {
			oldcontact.setPhone(address);
		}
		if (email != null && newcontact.validEmail(email)) {
			oldcontact.setEmail(email);
		}

	}

	/**
	 * query
	 * 
	 * @param restInf
	 * @param contactList
	 * @param queryContactList
	 */
	public String query(String restInf, String instruction, List<Contact> contactList) {
		Scanner s = null;
		String str = "";
		// name Jo Bloggs; birthday 08-07-1900; phone 88884444; address 9001 Chester
		// Crescent, Chatswood, NSW 2057
		s = new Scanner(restInf);
		String firstWord, line;
		while (s.hasNext()) {
			firstWord = s.next();
			if (s.hasNextLine()) {
				line = s.nextLine().trim();
				Iterator<Contact> iterator = contactList.iterator();
				str += setQueryBegin(instruction);
				while (iterator.hasNext()) {
					Contact r = iterator.next();
					if ("name".equals(firstWord)) {
						if (line.equals(r.getName())) {
							str = str + queryContact(r);
						}
					} else if ("birthday".equals(firstWord)) {
						if (line.equals(r.getBirthday())) {
							str += queryContact(r);
						}
					} else if ("phone".equals(firstWord)) {
						if (line.equals(r.getPhone())) {
							str += queryContact(r);
						}
					} else if ("address".equals(firstWord)) {
						if (line.equals(r.getAddress())) {
							str += queryContact(r);
						}
					} else if ("email".equals(firstWord)) {
						if (line.equals(r.getEmail())) {
							str += queryContact(r);
						}
					}
				}
				str += setQueryEND(restInf);
			}
		}
		s.close();
		return str;
	}

	public String setQueryBegin(String a) {
		return "====== " + a + " ======\n";
	}

	public String setQueryEND(String a) {
		return "====== end of " + a + " ======\n";
	}

	/**
	 * Stitching content
	 * 
	 * @param addContact
	 */
	public String queryContact(Contact r) {
		String str = "";
		if (r.validName(r.getName()) && r.getName() != null && r.getName() != "") {
			str += "name: " + r.getName() + "\n";
		}
		if (r.getAddress() != null && r.getAddress() != "") {
			str += "address: " + r.getAddress() + "\n";
		}
		if (r.validBirthday(r.getBirthday())) {
			str += "birthday: " + r.getBirthday() + "\n";
		}
		if (r.getPhone() != null && r.getPhone() != ""&& r.validPhone(r.getPhone())) {
			str += "phone: " + r.getPhone() + "\n";
		}
		if (r.getEmail() != null && r.getEmail() != "" && r.validEmail(r.getEmail())) {
			str += "email: " + r.getEmail() + "\n";
		}
		return str + "\r\n";
	}
}
