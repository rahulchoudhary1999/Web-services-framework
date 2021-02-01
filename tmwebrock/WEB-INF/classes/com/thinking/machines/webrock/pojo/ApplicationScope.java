package com.thinking.machines.webrock.pojo;
import javax.servlet.*;
public class ApplicationScope
{
private ServletContext servletContext;
public ApplicationScope(){
this.servletContext=null;
}
public void setAttribute(ServletContext servletContext)
{
this.servletContext=servletContext;
}
public ServletContext getAttribute()
{
return this.servletContext;
}
}