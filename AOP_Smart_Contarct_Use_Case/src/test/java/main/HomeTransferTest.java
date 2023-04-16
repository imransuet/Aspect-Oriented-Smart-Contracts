package main;
import hometransfer.Home;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final  class HomeTransferTest {

    @Test
    public void isEqual(){
        Home home = new Home("1", "FirstHome","3000","Elon Musk","200");
        assertThat(home).isEqualTo(home);
    }

    @Test
    public void nonEqual(){
        Home home = new Home("1", "FirstHome","3000","Elon Musk","200");
        Home home1 = new Home("2", "SecondHome","5000","Elon Musk","280");
        assertThat(home).isNotEqualTo(home1);
    }
}
