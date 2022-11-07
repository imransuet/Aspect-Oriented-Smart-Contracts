package roleobject;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        ArrayList<EmployeeCore> arr = employeeList();
        for(Employee emp : arr)
        {
            if(emp.hasRole("dev"))
            {
                Developer d = (Developer)emp.getRole("dev");
                d.doCoding();
            }
            if(emp.hasRole("tester"))
            {
                Tester t = (Tester) emp.getRole("tester");
                t.doTesting();
            }
        }
    }


    private static ArrayList<EmployeeCore> employeeList()
    {
        ArrayList<EmployeeCore> employeeList = new ArrayList<>();
        employeeList.add(new EmployeeCore("Haris ","1"));


        Tester t = new Tester();
        employeeList.get(0).addRole("tester",t);
        Developer d = new Developer();
        employeeList.get(0).addRole("dev",d);



        return employeeList;
    }

}