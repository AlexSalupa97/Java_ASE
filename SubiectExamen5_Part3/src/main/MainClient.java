package main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import clase.Avion;
import clase.AvionPasageri;
import clase.OperatiiInOutFisier;

public class MainClient {

	public static void main(String[] args) {
		List<String> cnp=new Vector<>();
		cnp.add("111");
		cnp.add("222");
		cnp.add("333");
		
		List<String> serieMrf=new Vector<>();
		serieMrf.add("111");
		serieMrf.add("222");
		serieMrf.add("333");
		
		AvionPasageri ap1=null;
		try {
			ap1=new AvionPasageri("1", 1, "1", 1, cnp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AvionPasageri ap2=null;
		try {
			ap2=new AvionPasageri("2", 2, "2", 2, cnp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(ap1.equals(ap2));
		
		@SuppressWarnings("unused")
		AvionPasageri ap3=null;
		try {
			ap3=(AvionPasageri) ap1.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(ap1.equals(ap3));
		
		List<Avion> lista=new ArrayList<>();
		lista.add(ap1);
		lista.add(ap2);
		
		
		OperatiiInOutFisier op=new OperatiiInOutFisier("Pasageri.txt","AvionPasageri");
		op.scrieObiectInFisierText(lista);
		
		List<Avion> listaCitita=op.citesteObiectDinFisierText();
		//System.out.println(listaCitita.size());
		
		
		final String adresa="localhost";
		short port=6700;
		
		Runnable r=()->
		{
			try 
			{
				Socket client=new Socket(adresa, port);
				while(true)
				{
					ObjectOutputStream os=new ObjectOutputStream(client.getOutputStream());
					os.writeObject(lista);
					os.flush();
					System.out.println("Legare cu serverul");
					client.close();
				}
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Client inchis");
			}
		};
		
		Thread t=new Thread(r);
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


