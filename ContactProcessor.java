package ECB19S2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactProcessor {

	private File phonebookfile;
	private File instructionfile;
	private File outputfile;
	private File reportfile;
	
	private ArrayList<Contact> contactList;
	private ContactHandler contactHandler;
	
	/**
	 * save the results of the "query" step
	 * 
	 * */
	String queryString = "";
	public ContactProcessor(String[] s) {
		phonebookfile = new File(s[0]);
		instructionfile = new File(s[1]);
		outputfile = new File(s[2]);
		reportfile = new File(s[3]);

		contactList = new ArrayList<Contact>();
		contactHandler = new ContactHandler();
	}

	// read instruction file
	public void readinstructionfile() {
		Scanner in;
		try {
			in = new Scanner(instructionfile);
			while (in.hasNextLine()) {
				String instruction = in.nextLine();
				// add name Jo Bloggs; birthday 08-07-1900; phone 88884444; address 9001 Chester
				// Crescent, Chatswood, NSW 2057
				Scanner sc = new Scanner(instruction);
				String firstWord, restInf;
				if (sc.hasNext()) {
					firstWord = sc.next();
					// add
					if (sc.hasNextLine()) {
						restInf = sc.nextLine();
						// restInf==>name Jo Bloggs; birthday 08-07-1900; phone 88884444; address 9001
						// Chester Crescent, Chatswood, NSW 2057
						if (firstWord.equalsIgnoreCase("add")) {
							contactHandler.add(restInf, contactList);
						} else if (firstWord.equalsIgnoreCase("delete")) {
							contactHandler.delete(restInf, contactList);
						} else if (firstWord.equalsIgnoreCase("query")) {
							String query = contactHandler.query(restInf, instruction, contactList);
							queryString += query;
						}
					} else {
						continue;
					}
					sc.close();
				} else {
					continue;
				}
			}
			in.close();
		} catch (Exception e) {
		}
	}

		// read phonebookfile content
	public void readphonebookfile() {
		Scanner in;
		try {
			in = new Scanner(phonebookfile);
			StringBuilder sb = new StringBuilder(); 
			List<String> list = new ArrayList<>();
			while (in.hasNextLine()) {
				String line = in.nextLine().trim(); 
				// finish a person
				if ("".equals(line) || null == line) { 
					list.add(sb.toString());
					sb.delete(0, sb.length());
					continue;
				}
				if (!"".equals(sb.toString())) {
					sb.append(";");
				}
				sb.append(line);
				if (!in.hasNextLine()) {
					list.add(sb.toString());
				}else {
					String substring = line.substring(line.length() - 1);
					if(",".equals(substring)) {
						String nline = in.nextLine().trim();
						sb.append(" "+nline);
						 nline = in.nextLine().trim();
						 if(!"".equals(nline) && null != nline) {
									sb.append(" " + nline);
						 }
					}
				}
			}
			in.close();
			Contact contact = null;
			for (String string : list) {
				// name James Smith;birthday 23-3-1989;address 60 Morris St, Summer Hill, NSW 2130
				String[] split = string.split(";"); // split() to separate
				contact = new Contact();
				for (String line : split) {
					String[] temp = line.split("\\s"); 
					String theOthers = "";
					if ("name".equalsIgnoreCase(temp[0])) {
						theOthers = content(temp);
						contact.setName(theOthers);
					} else if ("birthday".equalsIgnoreCase(temp[0])) {
						theOthers = content(temp);
						contact.setBirthday(theOthers);
					} else if ("address".equalsIgnoreCase(temp[0])) {
						theOthers = content(temp);
						contact.setAddress(theOthers);
					} else if ("phone".equalsIgnoreCase(temp[0])) {
						theOthers = content(temp);
						contact.setPhone(theOthers);
					} else if ("email".equalsIgnoreCase(temp[0])) {
						theOthers = content(temp);
						contact.setEmail(theOthers);
					}
				} // separate one by one
				contactList.add(contact); // add to contactList
			}
			// System.out.println(contactList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param temp
	 * @return
	 */
	public static String content(String[] temp) { // add space to each world
		int i = temp.length - 1;
		String name = temp[1];
		if (i > 0) {
			for (int j = 1; j < i; j++) {
				name += " " + temp[1 + j];
			}
		}
		return name;
	}
	
	// save “after add/delete” 's saveoutfile
	public void saveoutputfile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(outputfile)); 

			if (!contactList.isEmpty()) {
				int x=0;
				for (Contact r : contactList) {
					x++;
					
					if (r.getName() != null && r.getName() != "" && r.validName(r.getName()) 
						       && r.getBirthday() != null && r.getBirthday() != "" && r.validBirthday(r.getBirthday()) && r.checkDay(r.getBirthday())) {
						      out.write("name: " + r.getName()+"\n");
						      out.write("birthday: " + r.getBirthday()+"\n");
						     }else {
						      continue;
						     }
					if (r.getAddress() != null && r.validAddress(r.getAddress()) ) {
						out.write("address: " + r.getAddress()+"\n");
					}

					if (r.getPhone() != null && r.validPhone(r.getPhone())) {
						out.write("phone: " + r.ignorePhoneZero(r.getPhone())+"\n");
					}

					if(r.getEmail() != null && r.validEmail(r.getEmail())){
						out.write("email: " + r.getEmail()+"\n");
					}
					
					if(x!=contactList.size())
					
						out.write("\n");
					}
					
			out.flush();
			out.close();}
			}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// save “after query” 's reportfile
	public void savereportfile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(reportfile));
			out.write(queryString);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Contact friend : contactList) {
			sb.append(friend.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
