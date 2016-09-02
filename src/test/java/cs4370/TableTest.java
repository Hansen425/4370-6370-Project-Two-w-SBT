
/****************************************************************************************
 *@file TableTest.java
 *
 *@author Nick Klepp, Trent Walls, Jason Vinson, Theresa Miller
 */

import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.*;

public class TableTest extends TestCase{

    private Table movie;
    private Table movie2;
    private Table movie3;
    private Table movie4;
    private Table movie5;
    private Table movie6;
    
    protected void setUp(){
	movie = new Table ("movie", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");

	movie2 = new Table ("movie2", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");

	movie3 = new Table ("movie3", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");

	movie4 = new Table ("movie4", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");
	movie5 = new Table("movie5", "title year","String Integer","title year");

	/**	movie6 = new Table("movie", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");
	**/

	movie6 = new Table("movie6","title year length genre studioName producerNo","String Integer Integer String String Integer", "title year");
   
	
	Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
	Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
	Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
	Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
       	Comparable [] film4 = {"Star_Wars",1977};
	Comparable [] film5 = { "Star_Wars_2", 1980};
	Comparable [] film6 = { "Rocky", 1985};
	Comparable [] film7 = { "Rambo", 1978};

	movie.insert(film0);
	movie.insert(film1);
	movie.insert(film2);
	movie.insert(film3);

	movie2.insert(film0);
	movie2.insert(film1);   

	movie3.insert(film2);
	movie3.insert(film3);

	movie4.insert(film0);

	movie5.insert(film4);
	movie5.insert(film5);
	movie5.insert(film6);
	movie5.insert(film7);
    }
    
    

    @Test
    public void testMinus(){

	Table newMovie=movie.minus(movie2);
	Table newMovie2=movie.minus(movie6);
	Table newMovie3=movie.minus(movie);
	Table newMovie4=movie.minus(movie5);

	assertTrue(movie3.equals(newMovie));
	assertTrue(movie2.equals(movie));
	System.out.println("movie3: ");
	movie3.print();
	System.out.println("movie6: ");
	movie6.print();
	assertTrue(newMovie3.equals(movie6));
	assertTrue(movie4.equals(movie4));
    }

    @Test
    public void testIndexSelect(){
	KeyType key1=new KeyType(new Comparable[]{"Star_Wars",1977});
	Table newMovie=movie.select(key1);
	assertTrue(newMovie.equals(movie4));
    } 

    @Test
    public void testUnion(){
	assertTrue(movie.equals(movie2.union(movie3)));
	assertTrue(movie.equals(movie.union(movie)));
	assertTrue(movie.union(movie5)==null);
	assertTrue(movie.equals(movie.union(movie6)));
    }


    @Test
    public void testProject(){
	setUp();

	Table t_project1;
	Table t_project2;
	Table t_project3;
	Table t_project4;
	Table t_project5;

	Table t_project1_correct;
	Table t_project2_correct;
	Table t_project3_correct;
	Table t_project4_correct;

	Comparable [] project1_film0 = { "Star_Wars", 1977 };
	Comparable [] project1_film1 = { "Star_Wars_2", 1980 };
	Comparable [] project1_film2 = { "Rocky", 1985 };
	Comparable [] project1_film3 = { "Rambo", 1978 };

	Comparable [] project2_film0 = { 1977, "sciFi" };
	Comparable [] project2_film1 = { 1980, "sciFi" };
	Comparable [] project2_film2 = { 1985, "action"};
	Comparable [] project2_film3 = { 1978, "action"};

	Comparable [] project3_film0 = { 1977, "Star_Wars" };
	Comparable [] project3_film1 = { 1980, "Star_Wars_2" };
	Comparable [] project3_film2 = { 1985, "Rocky" };
	Comparable [] project3_film3 = { 1978, "Rambo" };

	//Comparable [] project4_film = null;

	System.out.println("\n\n Testing projection....");

	//Projection 1
	t_project1_correct = new Table ("Correct Movie 0",
					"title year",
					"String Integer",
					"title year");
	t_project1_correct.insert(project1_film0);
	t_project1_correct.insert(project1_film1);
	t_project1_correct.insert(project1_film2);
	t_project1_correct.insert(project1_film3);

	//Projection 2
	t_project2_correct = new Table ("Correct Movie 1",
					"year genre",
					"Integer String",
					"year genre");
	t_project2_correct.insert(project2_film0);
	t_project2_correct.insert(project2_film1);
	t_project2_correct.insert(project2_film2);
	t_project2_correct.insert(project2_film3);

	//Projection 3
	t_project3_correct = new Table ("Correct Movie 2",
					"year title",
					"Integer String",
					"year title");
	t_project3_correct.insert(project3_film0);
	t_project3_correct.insert(project3_film1);
	t_project3_correct.insert(project3_film2);
	t_project3_correct.insert(project3_film3);

	//Projection 4
	t_project4_correct = new Table ("Correct Movie 3",
					"",
					"",
					"");

	System.out.print("\n\n Test 0: Regular projection");
	t_project1 = movie.project ("title year");
	assertTrue(t_project1.equals(t_project1_correct));

	System.out.print("\n\n Test 1: Space between columns");
	t_project2 = movie.project ("year genre");
	assertTrue(t_project2.equals(t_project2_correct));

	System.out.print("\n\n Test 3: Order reversed");
	t_project3 = movie.project ("year table");
	assertTrue(t_project3.equals(t_project3_correct));

	System.out.print("Test 4: No columns");
	t_project4 = movie.project ("");
	assertTrue(t_project4.equals(t_project4_correct));

	System.out.print("Test 5: All columns");
	t_project5 = movie.project ("title year length genre studioName producerNo");
	assertTrue(t_project5.equals(movie));
  
    }
    
    @Test
    public void testEquiJoin(){
	//ARRANGE
	Table movie_cast = new Table ("movie_cast", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");

	Table movie2_cast = new Table ("movie2_cast", "title year actor1 actor2 actor3 actor4",
				  "String Integer String String String String", "title year");



	Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
	Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
	Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
	Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };

	movie_cast.insert(film0);
	movie_cast.insert(film1);
	movie_cast.insert(film2);
	movie_cast.insert(film3);
	movie_cast.print();

	Comparable [] joinFilm0 = {"Star_Wars", 1977, "Han", "Luke", "Yoda", "Bob"};
	Comparable [] joinFilm1 = {"Star_Wars_2", 1980, "Padme", "Jango", "Zam", "Joe"};
	movie2_cast.insert(joinFilm1);
	movie2_cast.insert(joinFilm0);

	movie2_cast.print();

	//ACT
	Table joinTable = movie_cast.join("title year", "title year", movie2_cast);//EquiJoin
	Table equiJoin_correct = new Table (
       				    "movie11", "title year length genre studioName producerNo title2 year2 actor1 actor2 actor3 actor4",
       				    "String Integer Integer String String Integer String Integer String String String String", "title year");

	Comparable [] eqJ_correct1 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345, "Star_Wars", 1977, "Han", "Luke", "Yoda", "Bob"};
	Comparable [] eqJ_correct2 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345, "Star_Wars_2", 1980, "Padme", "Jango", "Zam", "Joe"};
	equiJoin_correct.insert(eqJ_correct1);
	equiJoin_correct.insert(eqJ_correct2);
	System.out.println("Equijoin: ");
	equiJoin_correct.print();
	System.out.println("result of movie.join(\"title year\", \"title year\",movie2 :" );
	joinTable.print();
	assertTrue(equiJoin_correct.getTuples().size() == joinTable.getTuples().size());

	Table naturalJoin = movie_cast.join(movie2_cast);
	System.out.println("Natural Join Table: ");
	naturalJoin.print();

	assertTrue(equiJoin_correct.getTuples().size() == joinTable.getTuples().size());
	assertTrue(equiJoin_correct.equals(joinTable));
    }
    
}
