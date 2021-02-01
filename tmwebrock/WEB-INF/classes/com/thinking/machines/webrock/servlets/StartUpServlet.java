package com.thinking.machines.webrock.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.annotations.*;
public class StartUpServlet extends HttpServlet
{
private List<String> classList;
private List<Service> onStartUpServicesList;
private String startPackagePrefix;
private Map<String,Service> map;
private ArrayList<FieldHandler> fieldHandlerList;
public void init()
{
try{
this.map=new HashMap<>();
this.classList=new LinkedList<>();
this.fieldHandlerList=new ArrayList<>();
this.onStartUpServicesList=new ArrayList<>();
this.startPackagePrefix=getServletConfig().getInitParameter("SERVICE_PACKAGE_PREFIX");
//System.out.println(startPackage);
String maindirpath=getServletContext().getRealPath("")+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+this.startPackagePrefix;
//System.out.println(maindirpath);
File maindir = new File(maindirpath); 
if(maindir.exists() && maindir.isDirectory()) 
{ 
File arr[] = maindir.listFiles(); 
recursivePrint(arr,0,0);
}
//list pe iterate krenege
Class c;
Method methods[];
Field fields[];
String url;
Path a,b;
Service service;
GET getAnnotationFoundOnClass;
POST postAnnotationFoundOnClass;
GET getAnnotationFoundOnMethod;
POST postAnnotationFoundOnMethod;
FORWARD forwardToAnnotation;
OnStartUp onStartUpAnnotation;
InjectApplicationDirectory injectApplicationDirectory;
InjectSessionScope injectSessionScope;
InjectApplicationScope injectApplicationScope;
InjectRequestScope injectRequestScope;
AutoWired autoWired;
for(String className:classList)
{
c=Class.forName(className);
a=(Path)c.getAnnotation(com.thinking.machines.webrock.annotations.Path.class);
getAnnotationFoundOnClass=(GET)c.getAnnotation(com.thinking.machines.webrock.annotations.GET.class);
postAnnotationFoundOnClass=(POST)c.getAnnotation(com.thinking.machines.webrock.annotations.POST.class);
injectApplicationDirectory=(InjectApplicationDirectory)c.getAnnotation(com.thinking.machines.webrock.annotations.InjectApplicationDirectory.class);
injectSessionScope=(InjectSessionScope)c.getAnnotation(com.thinking.machines.webrock.annotations.InjectSessionScope.class);
injectApplicationScope=(InjectApplicationScope)c.getAnnotation(com.thinking.machines.webrock.annotations.InjectApplicationScope.class);
injectRequestScope=(InjectRequestScope)c.getAnnotation(com.thinking.machines.webrock.annotations.InjectRequestScope.class);
fields=c.getDeclaredFields();
FieldHandler fieldHandler;
for(Field field:fields)
{
autoWired=(AutoWired)field.getAnnotation(com.thinking.machines.webrock.annotations.AutoWired.class);
if(autoWired!=null)
{
fieldHandler=new FieldHandler();
fieldHandler.setProperty(field);
fieldHandler.setName(autoWired.name());
this.fieldHandlerList.add(fieldHandler);
}
}
methods=c.getDeclaredMethods();
for(Method m:methods)
{
b=(Path)m.getAnnotation(com.thinking.machines.webrock.annotations.Path.class);
if(b!=null)
{
url=a.value()+b.value();
service=new Service();
service.setPath(url);
service.setServiceClass(c);
service.setServiceMethod(m); 
service.setFieldHandlerList(fieldHandlerList); 
if(injectApplicationDirectory!=null)service.setInjectApplicationDirectory(true);
if(injectSessionScope!=null)service.setInjectSessionScope(true);   
if(injectApplicationScope!=null)service.setInjectApplicationScope(true);   
if(injectRequestScope!=null)service.setInjectRequestScope(true);                                                      
forwardToAnnotation=(FORWARD)m.getAnnotation(com.thinking.machines.webrock.annotations.FORWARD.class);
if(forwardToAnnotation!=null)
{
service.setForwardTo(a.value()+forwardToAnnotation.value());
}
if(getAnnotationFoundOnClass==null && postAnnotationFoundOnClass==null)
{
getAnnotationFoundOnMethod=(GET)m.getAnnotation(com.thinking.machines.webrock.annotations.GET.class);
postAnnotationFoundOnMethod=(POST)m.getAnnotation(com.thinking.machines.webrock.annotations.POST.class);
if(getAnnotationFoundOnMethod!=null)
{
service.setIsGetAllowed(true);
}
if(postAnnotationFoundOnMethod!=null)
{
service.setIsPostAllowed(true);
}
}
else
{
if(getAnnotationFoundOnClass!=null)
{
service.setIsGetAllowed(true);
service.setIsPostAllowed(false);
}
if(postAnnotationFoundOnClass!=null)
{
service.setIsPostAllowed(true);
service.setIsGetAllowed(false);
}
}
onStartUpAnnotation=(OnStartUp)m.getAnnotation(com.thinking.machines.webrock.annotations.OnStartUp.class); 
if(onStartUpAnnotation!=null)
{
Class returnType=m.getReturnType();
Parameter params[]=m.getParameters();
if(returnType.getName().equals("void") && params.length==0)
{
//System.out.println("Return type: "+returnType.getName()+" , invalid parameter value: "+params.length);
//System.out.println(onStartUpAnnotation.priority());
service.setRunOnStartUp(url);
service.setPriority(onStartUpAnnotation.priority());
onStartUpServicesList.add(service);
}
else
{
System.out.println("Invalid return type: "+returnType.getName()+" , invalid parameter value: "+params.length);
}
}
map.put(url,service);
}
}
}
//put map in appicationscope
getServletContext().setAttribute("model",map);
int z;
Service ss;
for(int y=1;y<onStartUpServicesList.size();y++)
{
ss=onStartUpServicesList.get(y);
z=y-1;
while(z>=0 && ss.getPriority()<onStartUpServicesList.get(z).getPriority())
{
onStartUpServicesList.set(z+1,onStartUpServicesList.get(z));
z--;
}
onStartUpServicesList.set(z+1,ss);	
}
for(Service s:onStartUpServicesList)
{
//System.out.println(s.getPriority());
Class serviceClass=s.getServiceClass();
Method serviceMethod=s.getServiceMethod();
serviceMethod.invoke(serviceClass.newInstance());
}
}catch(Exception e)
{
System.out.println("Exception"+e);
}
}
public void recursivePrint(File[] arr,int index,int level)
{
// terminate condition 
if(index==arr.length) return; 
if(arr[index].isFile()) 
{
if(arr[index].getName().endsWith(".class") )
{
String packageName=arr[index].getAbsolutePath();
int index1=packageName.indexOf(this.startPackagePrefix);
packageName=packageName.substring(index1,packageName.length()-6).replace("\\",".");
classList.add(packageName);
}
}       
else if(arr[index].isDirectory()) 
{ 
recursivePrint(arr[index].listFiles(), 0, level + 1); 
} 
recursivePrint(arr,++index, level); 
}
}