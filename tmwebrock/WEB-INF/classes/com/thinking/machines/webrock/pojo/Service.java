package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
import java.util.*;
public class Service implements java.io.Serializable
{
private Class serviceClass;
private Method serviceMethod;
private boolean isGetAllowed;
private boolean isPostAllowed;
private String forwardTo;
private String path;
private String runOnStartUp;
private int priority;
private boolean injectApplicationDirectory;
private boolean injectSessionScope;
private boolean injectApplicationScope;
private boolean injectRequestScope;
private ArrayList<FieldHandler> fieldHandlerList;
public Service()
{
this.serviceClass=null;
this.serviceMethod=null;
this.path="";
this.isGetAllowed=false;
this.isPostAllowed=false;
this.forwardTo=null;
this.runOnStartUp=null;
this.priority=-1;
this.injectApplicationDirectory=false;
this.injectSessionScope=false;
this.injectApplicationScope=false;
this.injectRequestScope=false;
this.fieldHandlerList=null;
}
public void setFieldHandlerList(ArrayList<FieldHandler> fieldHandlerList)
{
this.fieldHandlerList=fieldHandlerList;
}
public ArrayList<FieldHandler> getFieldHandlerList()
{
return this.fieldHandlerList;
}
public void setInjectApplicationDirectory(boolean injectApplicationDirectory)
{
this.injectApplicationDirectory=injectApplicationDirectory;
}
public boolean getInjectApplicationDirectory()
{
return this.injectApplicationDirectory;
}

public void setInjectSessionScope(boolean injectSessionScope)
{
this.injectSessionScope=injectSessionScope;
}
public boolean getInjectSessionScope()
{
return this.injectSessionScope;
}

public void setInjectApplicationScope(boolean injectApplicationScope)
{
this.injectApplicationScope=injectApplicationScope;
}
public boolean getInjectApplicationScope()
{
return this.injectApplicationScope;
}

public void setInjectRequestScope(boolean injectRequestScope)
{
this.injectRequestScope=injectRequestScope;
}
public boolean getInjectRequestScope()
{
return this.injectRequestScope;
}

public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setServiceMethod(Method serviceMethod)
{
this.serviceMethod=serviceMethod;
}
public Method getServiceMethod()
{
return this.serviceMethod;
}
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setIsPostAllowed(boolean isPostAllowed)
{
this.isPostAllowed=isPostAllowed;
}
public boolean getIsPostAllowed()
{
return this.isPostAllowed;
}
public void setIsGetAllowed(boolean isGetAllowed)
{
this.isGetAllowed=isGetAllowed;
}
public boolean getIsGetAllowed()
{
return this.isGetAllowed;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public String getForwardTo()
{
return this.forwardTo;
}
public void setRunOnStartUp(String runOnStartUp)
{
this.runOnStartUp=runOnStartUp;
}
public String getRunOnStartUp()
{
return this.runOnStartUp;
}
public void setPriority(int priority)
{
this.priority=priority;
}
public int getPriority()
{
return this.priority;
}
}