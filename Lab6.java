/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softcons.citysearch2.view;

import static com.mysql.cj.core.MysqlType.NULL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import softcons.citysearch2.model.City;

/**
 *
 * @author ibrah   
 */
public class Lab6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        // TODO code application logic here
        String csv = "GeoLiteCity-Location.csv";
        String line;
        int x=0;
        List<String> list = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {

            
            while((line = br.readLine()) != null)
            {
                list.add(line);
            }

//            String[] stringArr = list.toArray(new String[0]);
            //System.out.println(list.get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
                SessionFactory factory=new Configuration().configure().buildSessionFactory();
		//creating session object  
		Session session=factory.openSession();  
			//creating transaction object  
			//Transaction t=session.beginTransaction();  
//			Employee e1=new Employee();  
//			e1.setId(116);  
//			e1.setFirst_name("Fahad");  
//			e1.setLast_name("Satti");  
//			session.persist(e1);//persisting the object  
                        int temp = 2;
                        int size = list.size();
                        City city; //= new City();
                        String[] data;
                        //Data is enetered once
//                        while(temp!=size){
//                            city = new City();
//                            Transaction t=session.beginTransaction();
//                            data = list.get(temp).split(",");
//                            data[1] = data[1].substring(1, data[1].length()-1);
//                            data[2] = data[2].substring(1, data[2].length()-1);
//                            data[3] = data[3].substring(1, data[3].length()-1);
//                            data[4] = data[4].substring(1, data[4].length()-1);
//                            //System.out.println(data.length);
//                            city.setLocId(Integer.parseInt(data[0]));
//                            city.setCountry(data[1]);
//                            city.setRegion(data[2]);
//                            city.setCity(data[3]);
//                            if(!data[4].equals("\"\"")&& !data[4].equals("")){  
//                            city.setPostalCode(data[4]);}
//                            city.setLatitude(Float.valueOf(data[5]));
//                            city.setLongitude(Float.valueOf(data[6]));
//                            if(data.length>7 && !data[7].equals("\"\"") && !data[7].equals("")){  
//                            city.setMetroCode(Integer.parseInt(data[7]));}
//                            if(data.length>8 && !data[8].equals("\"\"")&& !data[8].equals("")){  
//                            city.setAreaCode(Integer.parseInt(data[8]));}
//                            session.persist(city);
//                            temp++;
//                            t.commit();//transaction is commited  
//                            city = null;
//                        }
                           //Query Wali Line Jahan User Input Deta Hai
                        Transaction t = null;// = session.beginTransaction();
                        //city = new City();
                        double cityarr[]={0,0,0,0,0};
                        int it=0;
                        double userlat=0;
                        double userlng=0;
                        
                        double max=0;
                        int itey=0;
                        double mylat[]={0,0,0,0,0};
                        double mylng[]={0,0,0,0,0};
                        System.out.println("Enter City:");
                        String inputcity = scanf.nextLine();
                        System.out.println("Enter 2nd City:");
                        String inputcity1 = scanf.nextLine();
                        try{
                            t = session.beginTransaction();
                            List cities = session.createQuery("FROM City").list(); 
                            for (Iterator iterator = cities.iterator(); iterator.hasNext();){
                               city = (City) iterator.next();
                               
                               if (inputcity.equals(city.getCity())){
                                System.out.print("Location Id: " + city.getLocId()); 
                                System.out.print("  Latitude: " + city.getLatitude()); 
                                System.out.println("  Longitude: " + city.getLongitude());
                                userlat = city.getLatitude();
                                userlng = city.getLongitude();
                               }
                               
                            }
                             List cities1 = session.createQuery("FROM City").list(); 
                            for (Iterator iterator = cities1.iterator(); iterator.hasNext();){
                               city = (City) iterator.next();
                               
                               if (inputcity1.equals(city.getCity())){
                                System.out.println("Distance between 2 cities: "+abs(distance(userlat,userlng,city.getLatitude(),city.getLongitude()))*10000/1.150 + " miles");
                                
                               }
                               
                            }
                        
                            city = null;
                            List citiess = session.createQuery("FROM City").list(); 
                            for (Iterator iterator = citiess.iterator(); iterator.hasNext();){
                               city = (City) iterator.next();
                               //Debugging
//                               if (inputcity.equals(city.getCity())){
//                                System.out.print("Location Id: " + city.getLocId()); 
//                                System.out.print("  Latitude: " + city.getLatitude()); 
//                                System.out.println("  Longitude: " + city.getLongitude());
//                               }
                               if (it<5)
                               {
                                   //Debugging
                                   //System.out.println("Inside 2nd for loop");
                                   cityarr[it]= distance(userlat,userlng,city.getLatitude(),city.getLongitude());
                                   mylat[it]=city.getLatitude();
                                   mylng[it]=city.getLongitude();
//                                   if (cityarr[it]>max)
//                                       max=cityarr[it];
                               }
                               else
                               {
                                   max =0;
                                   for (int i = 0; i < 5; i++) {
                                        if (cityarr[i] > max) {
                                          max = cityarr[i];
                                          itey = i;
                                          //Debugging
                                          //System.out.println("Itey: "+itey);
                                        }
                                   }
                                   if (distance(userlat,userlng,city.getLatitude(),city.getLongitude())<max && !city.getCity().equals(inputcity)){
                                       cityarr[itey]=distance(userlat,userlng,city.getLatitude(),city.getLongitude());
                                       //Debugging
                                       //System.out.println("Itey: "+itey);
                                       mylat[itey]=city.getLatitude();
                                       mylng[itey]=city.getLongitude();
                                       //Debugging
//                                       System.out.println("MAX: "+max);
//                                       System.out.println("Distance: "+cityarr[itey]);
//                                       System.out.println("yeh lat and long hain: "+mylat[itey]+" "+mylng[itey]);
                                   }
                               }
                               it++;
                            
                        }
                            city = null;
                            //Debugging
//                            System.out.println("\nTest for lat and long");
//                            System.out.println("Lat: "+mylat[0]+" Long: "+mylng[0]);
//                            System.out.println("Lat: "+mylat[1]+" Long: "+mylng[1]);
//                            System.out.println("Lat: "+mylat[2]+" Long: "+mylng[2]);
//                            System.out.println("Lat: "+mylat[3]+" Long: "+mylng[3]);
//                            System.out.println("Lat: "+mylat[4]+" Long: "+mylng[4]);
                            System.out.println("Closest cities are");
                            List citiesss = session.createQuery("FROM City").list(); 
                            for (Iterator iterator = citiesss.iterator(); iterator.hasNext();){
                               city = (City) iterator.next();
                               
                               if (mylat[0]==city.getLatitude() && mylng[0]==city.getLongitude())
                               {
                                   System.out.println(city.getCountry()+" " +city.getCity()+" "+city.getLatitude()+" "+city.getLongitude());
                               }
                               
                               else if (mylat[1]==city.getLatitude() && mylng[1]==city.getLongitude())
                               {
                                   System.out.println(city.getCountry()+" " +city.getCity()+" "+city.getLatitude()+" "+city.getLongitude());
                               }
                               else if (mylat[2]==city.getLatitude() && mylng[2]==city.getLongitude())
                               {
                                   System.out.println(city.getCountry()+" " +city.getCity()+" "+city.getLatitude()+" "+city.getLongitude());
                               }
                               else if (mylat[3]==city.getLatitude() && mylng[3]==city.getLongitude())
                               {
                                   System.out.println(city.getCountry()+" " +city.getCity()+" "+city.getLatitude()+" "+city.getLongitude());
                               }
                               else if (mylat[4]==city.getLatitude() && mylng[4]==city.getLongitude())
                               {
                                   System.out.println(city.getCountry()+" " +city.getCity()+" "+city.getLatitude()+" "+city.getLongitude());
                               }
                               
                               
                            }
                        }
                        catch (HibernateException e) {
                                if (t!=null) t.rollback();
                                e.printStackTrace(); 
                             }
			t.commit();//transaction is commited  
		session.close(); 
        
        //System.out.println(distance());
        
    }
    
    public static double distance(double lat1, double lng1, double lat2, double lng2)
    {
        double pk = (double) (180/Math.PI);
        double B1 = lat1 / pk;
        double B2 = lat2 / pk;
        double dL = (lng1-lng2) / pk;

        double t1 = (double) (Math.cos(B1)*Math.cos(B2)*Math.cos(dL));
        double t2 = (double) (Math.sin(B1)*Math.sin(B2));

        double tt = (double) Math.acos(t1 + t2);
        return tt;
    }
}
