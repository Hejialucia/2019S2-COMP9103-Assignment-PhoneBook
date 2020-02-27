package ECB19S2;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Contact {
	// instance fields
 	private String name;
	private String phone;
	private String address;
	private String email;
	private String birthday;

	// check name
	public boolean validName(String name) {
		if (name ==null || name == "" ) {
			return false;
		}
		String newName = name.replaceAll(" ", "");
		if (newName != null && newName.matches("[a-zA-Z]+")) {
			return true;
		} else {
			return false;
		}
	}
	
	 //check birthday
	public boolean validBirthday(String birthday) {
		if (birthday ==null || birthday == "" ) {
			return false;
		}
		String dateStr = birthday;
		String[] temp;
		if (dateStr.matches("(\\d){1,2}-(\\d){1,2}-(\\d){4}")) {
			temp = dateStr.split("-");
			if (temp.length == 3) {
				for (int i = 0; i < 2; ++i) {
					if (temp[i].length() < 2)
						temp[i] = "0" + temp[i];
				}
				dateStr = temp[0] + "-" + temp[1] + "-" + temp[2];
			}
			this.setBirthday(dateStr);
			return true;
		}
		else {
			return false;
		}
	}
	
	// check the day
	public boolean checkDay(String birthday) {
	    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
	    try {
	        sd.setLenient(false);
	        sd.parse(birthday);
	    } catch (Exception e) {
	        return false;
	    }
	    String str = birthday.substring(birthday.length() - 4);
	    Calendar date = Calendar.getInstance();
	    String year = String.valueOf(date.get(Calendar.YEAR));
	    if (Integer.parseInt(str) > Integer.parseInt(year)) {
	        return false;
	    } else return true;
	}

	// check email
	public boolean validEmail(String email) {
		if (email !=null && email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
			return true;
		}else {
			return false;
		}
		}
	
	// check phone
	public boolean validPhone(String phone) {
		if (phone != null && phone.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
			return true;
		} else {
			return false;
		}
	}
	
	// phone ignore 0
	public  String ignorePhoneZero(String phone) {
		String newPhone = phone.replaceFirst("^0*", "");
		return newPhone;
	}
	
	// check address
 	public boolean validAddress(String address) {
    if (address != null ) {
    String num = address.substring(address.length() - 4);
    try{
        int parseInt = Integer.parseInt(num);
    return true;
         }catch(Exception e) {
    return false;
   }
   } 
    return false;
 }

 	// get and set
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	//toString
	@Override
	public String toString() {
		return "Contact [name=" + name + ", phone=" + phone + ", address=" + address + ", email=" + email
				+ ", birthday=" + birthday + "]";
	}
}
