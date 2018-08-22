package main;

import base.DBService;
import base.datasets.AddressDataSet;
import base.datasets.PhoneDataSet;
import base.datasets.UserDataSet;
import dbService.DBServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.CacheInfo;
import servlet.CacheServlet;
import servlet.LoginServlet;

import java.util.List;


public class Main {

    private static final String PUBLIC_HTML = "public_html";
    private static final int PORT = 8090;

    public static void main(String[] args) throws Exception{
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);


        dbService.save(new UserDataSet("Roma", 27, new AddressDataSet("Lukina"),
                new PhoneDataSet("+79026863591"),
                new PhoneDataSet("+79524426980")));
        dbService.save(new UserDataSet("Sasha", 36, new AddressDataSet("Gagarina"),
                new PhoneDataSet("+79050112636"),
                new PhoneDataSet("+79151576325"),
                new PhoneDataSet("+79084783148")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.read(2);
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        dataSets.forEach(System.out::println);

        dataSet = dbService.read(2);
        System.out.println(dataSet);

        dbService.shutdown();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new LoginServlet("anonymus", "****")),
                "/login");
        CacheInfo info = new CacheInfo(dbService.getCacheSize(), dbService.getCacheHit(), dbService.getCahceMiss());
        context.addServlet(new ServletHolder(new CacheServlet(info)), "/cache");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));
        server.start();
        server.join();
    }
}
