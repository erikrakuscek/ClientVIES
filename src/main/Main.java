package main;

import java.util.Scanner;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.soap.SOAPFaultException;

import eu.europa.ec.taxud.vies.services.checkvat.CheckVatPortType;
import eu.europa.ec.taxud.vies.services.checkvat.CheckVatService;

public class Main {

	public static void main(String[] args) {
		CheckVatService service = new CheckVatService();
		CheckVatPortType port = service.getCheckVatPort();

		// Input country code and VAT number in command line
		Scanner sc = new Scanner(System.in);
		System.out.println("Country code: ");
		String cc = sc.nextLine();
		System.out.println("VAT number: ");
		String vn = sc.nextLine();
		sc.close();

		Holder<String> countryCode = new Holder<String>(cc);
		Holder<String> vatNumber = new Holder<String>(vn);
		Holder<XMLGregorianCalendar> requestDate = new Holder<XMLGregorianCalendar>();
		Holder<Boolean> valid = new Holder<Boolean>();
		Holder<String> name = new Holder<String>();
		Holder<String> address = new Holder<String>();

		try {
			port.checkVat(countryCode, vatNumber, requestDate, valid, name, address);

			if (!valid.value) {
				System.out.println("VAT number is invalid.");
			} else {
				// Print time of request and name and address based on given
				// country code and VAT number
				System.out.println("Time of request: " + requestDate.value);
				System.out.println("Name: " + name.value);
				System.out.println("Address: " + address.value);
			}

		} catch (SOAPFaultException ex) {
			System.out.println("Country code is invalid.");
		}

	}

}
