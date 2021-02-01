package com.thinking.machines.webrock.servlets;
import com.thinking.machines.webrock.pojo.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;	
public class TMWebRock extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
String url=request.getRequestURL().toString();
int index=url.indexOf("service");
url=url.substring(index+7);
Map<String,Service> map=(Map<String,Service>)getServletContext().getAttribute("model");
//for(Map.Entry<String,Service> entry:map.entrySet())System.out.println(entry.getKey()+","+entry.getValue());
if(map.containsKey(url))
{
Service service=map.get(url);
if(service.getIsGetAllowed()!=false || service.getIsPostAllowed()!=false)
{
if(service.getIsGetAllowed()==false)
{
//send error
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
return;
}
}
Class serviceClass=service.getServiceClass();
Method serviceMethod=service.getServiceMethod();

//Autowiring
Class property;
Object object;
String methodName;
String propertyName;
String firstLetter;
String remainingLetter;
Method setterMethod;
ArrayList<FieldHandler> fieldHandlerList=service.getFieldHandlerList(); 
for(FieldHandler fieldHandler:fieldHandlerList)
{
//System.out.println(fieldHandler.getProperty().getType().getSimpleName()+","+fieldHandler.getName());
property=fieldHandler.getProperty().getType();
object=request.getAttribute(fieldHandler.getName());
if(property.isInstance(object))
{
propertyName=property.getSimpleName();
firstLetter=propertyName.substring(0,1).toUpperCase();
remainingLetter=propertyName.substring(1);
methodName="set"+firstLetter+remainingLetter;
Class params[]=new Class[1];
params[0]=property;
setterMethod=serviceClass.getMethod(methodName,params);
setterMethod.invoke(serviceClass.newInstance(),object);
}
}
Object obj=serviceMethod.invoke(serviceClass.newInstance());
if(service.getInjectSessionScope())
{
HttpSession session=request.getSession();
SessionScope sessionScope=new SessionScope();
sessionScope.setAttribute(session);
Method setSessionScope=serviceClass.getMethod("setSessionScope",SessionScope.class);
setSessionScope.invoke(serviceClass.newInstance(),sessionScope);
}
if(service.getInjectApplicationScope())
{
ServletContext servletContext=getServletContext();
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setAttribute(servletContext);
Method setApplicationScope=serviceClass.getMethod("setApplicationScope",ApplicationScope.class);
setApplicationScope.invoke(serviceClass.newInstance(),applicationScope);
}
if(service.getInjectRequestScope())
{
RequestScope requestScope=new RequestScope();
requestScope.setAttribute(request);
Method setRequestScope=serviceClass.getMethod("setRequestScope",RequestScope.class);
setRequestScope.invoke(serviceClass.newInstance(),requestScope);
}
if(service.getInjectApplicationDirectory())
{
File directory= new File(getServletContext().getRealPath(" "));
ApplicationDirectory applicationDirectory=new ApplicationDirectory(directory);
Method setApplicationDirectory=serviceClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setApplicationDirectory.invoke(serviceClass.newInstance(),applicationDirectory);
}
//System.out.println("Response: "+obj);
if(service.getForwardTo()!=null)
{
String forwardURL=request.getRequestURL().toString().substring(0,32);
String requestURL[]=service.getForwardTo().split("/");
if(map.containsKey(service.getForwardTo()))
{
//String rr=requestURL[2];
//System.out.println(rr);
//RequestDispatcher requestDispatcher=request.getRequestDispatcher(rr);
//requestDispatcher.forward(request,response);
forwardURL=forwardURL+"service/"+requestURL[1]+"/"+requestURL[2];
response.sendRedirect(forwardURL);
}
else
{
forwardURL=forwardURL+requestURL[2];
response.sendRedirect(forwardURL);
}
}
}
else
{
response.sendError(HttpServletResponse.SC_NOT_FOUND);
}
}catch(Exception e)
{
System.out.println(e);
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
String url=request.getRequestURL().toString();
int index=url.indexOf("service");
url=url.substring(index+7);
Map<String,Service> map=(Map<String,Service>)getServletContext().getAttribute("model");
//for(Map.Entry<String,Service> entry:map.entrySet())System.out.println(entry.getKey()+","+entry.getValue());
if(map.containsKey(url))
{
Service service=map.get(url);
if(service.getIsGetAllowed()!=false || service.getIsPostAllowed()!=false)
{
if(service.getIsPostAllowed()==false)
{
//send error
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
return;
}
}
Class serviceClass=service.getServiceClass();
Method serviceMethod=service.getServiceMethod();

//Autowiring
Class property;
Object object;
String methodName;
String propertyName;
String firstLetter;
String remainingLetter;
Method setterMethod;
ArrayList<FieldHandler> fieldHandlerList=service.getFieldHandlerList(); 
for(FieldHandler fieldHandler:fieldHandlerList)
{
//System.out.println(fieldHandler.getProperty().getType().getSimpleName()+","+fieldHandler.getName());
property=fieldHandler.getProperty().getType();
object=request.getAttribute(fieldHandler.getName());
if(property.isInstance(object))
{
propertyName=property.getSimpleName();
firstLetter=propertyName.substring(0,1).toUpperCase();
remainingLetter=propertyName.substring(1);
methodName="set"+firstLetter+remainingLetter;
Class params[]=new Class[1];
params[0]=property;
setterMethod=serviceClass.getMethod(methodName,params);
setterMethod.invoke(serviceClass.newInstance(),object);
}
}
Object obj=serviceMethod.invoke(serviceClass.newInstance());
if(service.getInjectSessionScope())
{
HttpSession session=request.getSession();
SessionScope sessionScope=new SessionScope();
sessionScope.setAttribute(session);
Method setSessionScope=serviceClass.getMethod("setSessionScope",SessionScope.class);
setSessionScope.invoke(serviceClass.newInstance(),sessionScope);
}
if(service.getInjectApplicationScope())
{
ServletContext servletContext=getServletContext();
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setAttribute(servletContext);
Method setApplicationScope=serviceClass.getMethod("setApplicationScope",ApplicationScope.class);
setApplicationScope.invoke(serviceClass.newInstance(),applicationScope);
}
if(service.getInjectRequestScope())
{
RequestScope requestScope=new RequestScope();
requestScope.setAttribute(request);
Method setRequestScope=serviceClass.getMethod("setRequestScope",RequestScope.class);
setRequestScope.invoke(serviceClass.newInstance(),requestScope);
}
if(service.getInjectApplicationDirectory())
{
File directory= new File(getServletContext().getRealPath(" "));
ApplicationDirectory applicationDirectory=new ApplicationDirectory(directory);
Method setApplicationDirectory=serviceClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setApplicationDirectory.invoke(serviceClass.newInstance(),applicationDirectory);
}
//System.out.println("Response: "+obj);
if(service.getForwardTo()!=null)
{
String forwardURL=request.getRequestURL().toString().substring(0,32);
String requestURL[]=service.getForwardTo().split("/");
if(map.containsKey(service.getForwardTo()))
{
//String rr=requestURL[2];
//System.out.println(rr);
//RequestDispatcher requestDispatcher=request.getRequestDispatcher(rr);
//requestDispatcher.forward(request,response);
forwardURL=forwardURL+"service/"+requestURL[1]+"/"+requestURL[2];
response.sendRedirect(forwardURL);
}
else
{
forwardURL=forwardURL+requestURL[2];
response.sendRedirect(forwardURL);
}
}
}
else
{
response.sendError(HttpServletResponse.SC_NOT_FOUND);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}