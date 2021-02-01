package com.thinking.machines.webrock.pojo;
import javax.servlet.http.*;
public class SessionScope
{
private HttpSession httpSession;
public SessionScope(){
this.httpSession=null;
}
public void setAttribute(HttpSession httpSession)
{
this.httpSession=httpSession;
}
public HttpSession getAttribute()
{
return this.httpSession;
}
}