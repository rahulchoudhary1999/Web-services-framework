package bobby.com.thinking.machines.test;
import com.thinking.machines.webrock.annotations.*;
@Path("/Indore")
@GET
public class aaa{
@AutoWired(name="/xyz")
private bbb b1;
@Path("/Ujjain")
@FORWARD("/test1.html")
@OnStartUp(priority=2)
public void add()
{
return;
}
@OnStartUp(priority=1)
@Path("/Pune")
public void sub()
{
return;
}
}