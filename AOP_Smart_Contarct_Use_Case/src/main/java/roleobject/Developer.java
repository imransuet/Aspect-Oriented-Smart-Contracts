package roleobject;

public class Developer  extends EmployeeRole {
    Developer()
    {

    }

    Developer(EmployeeCore emp)
    {
        this.empCore=emp;
    }

    public void doCoding()
    {
        System.out.println(empCore.getName() + " I am Developing");

    }
}
