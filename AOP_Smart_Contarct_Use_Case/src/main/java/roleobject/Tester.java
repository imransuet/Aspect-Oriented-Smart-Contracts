package roleobject;

public class Tester extends EmployeeRole {

    Tester()
    {

    }

    Tester(EmployeeCore emp)
    {
        this.empCore=emp;
    }

    public void doTesting()
    {
        System.out.println(empCore.getName() + " I am testing");

    }
}
