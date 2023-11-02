package massimomauro.S5L3JUnit;

import massimomauro.S5L3JUnit.entities.Menu;
import massimomauro.S5L3JUnit.entities.MenuElement;
import massimomauro.S5L3JUnit.entities.Order;
import massimomauro.S5L3JUnit.entities.Table;
import massimomauro.S5L3JUnit.enums.OrderStatus;
import massimomauro.S5L3JUnit.enums.TableStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class S5L3JUnitApplication {

	public static void main(String[] args) {

		SpringApplication.run(S5L3JUnitApplication.class, args);
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(S5L3JUnitApplication.class);
		Random random= new Random();
		Scanner in = new Scanner(System.in);
		int personNum;
		Table tableTaken= null;


		app:
		while(true){
			askPersonNum:

			System.out.println("Buongiorno/Buonasera in quanti siete?");
			try{

				personNum = Integer.parseInt(in.nextLine());
				List<Table> tables = (List<Table>) ctx.getBean("getTables");
				List <Table> freeTable = tables.stream().filter(table -> table.getStatus()== TableStatus.LIBERO).toList();
				long idTable = 0;
				for (int i = 0; i < freeTable.size(); i++) {
					if(freeTable.get(i).getSeatingCapacity()>= personNum){
						idTable = freeTable.get(i).getId();
						freeTable.get(i).setStatus(TableStatus.OCCUPATO);
						tableTaken=freeTable.get(i);
						break;
					}
				}
				if(idTable==0){
					System.err.println("I tavoli sono tutti occupati!");
					break app;
				}

				System.out.println("prego sedetivi pure al tavolo N°"+idTable);

				List<MenuElement> orderList= new ArrayList<>();
				Menu menu = (Menu) ctx.getBean("getMenu");
				int orderNum = random.nextInt(6)+1;
				for (int i = 0; i <orderNum ; i++) {
					orderList.add(menu.getMenu().get(random.nextInt(menu.getMenu().size())));
				}

				Order order= new Order(random.nextLong(), tableTaken, orderList, OrderStatus.IN_CORSO, LocalTime.now(), personNum);
				System.out.println(order);

			}catch(Exception e){
				System.err.println(e.getMessage());
			}

		}
		in.close();
		ctx.close();
	}

}
