package com.leopaulmartin.spring.leboncoinecole.config;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.*;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AddressRepository;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AnnouncementRepository;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
import com.leopaulmartin.spring.leboncoinecole.services.UserService;
import com.leopaulmartin.spring.leboncoinecole.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private CategoryService categoryService;
	//	@Autowired
//	private PhoneNumberRepository phoneNumberRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private AnnouncementRepository announcementRepository;

	@Override
	public void run(String... strings) {
		createCategories();
		createSchoolsTest();
		createStudentMiageTest();

		// student in school
		School school = schoolService.getSchoolById(1L);
		Student student = studentService.getStudentById(1L);
		student.setSchool(school);

		// Announcement & Categories
		// Announcement
		Announcement ann1 = new Announcement(Utils.generateAnnounceTitle(), Utils.generateAnnounceDescription(), getRandomCategory(), Utils.getRandomPrice100());
		Announcement ann2 = new Announcement(Utils.generateAnnounceTitle(), Utils.generateAnnounceDescription(), getRandomCategory(), Utils.getRandomPrice100());
		Announcement ann3 = new Announcement(Utils.generateAnnounceTitle(), Utils.generateAnnounceDescription(), getRandomCategory(), Utils.getRandomPrice100());
		Announcement ann4 = new Announcement(Utils.generateAnnounceTitle(), Utils.generateAnnounceDescription(), getRandomCategory(), Utils.getRandomPrice100());
		ann4.makeItaSearch();
		Announcement ann5 = new Announcement(Utils.generateAnnounceTitle(), Utils.generateAnnounceDescription(), getRandomCategory(), Utils.getRandomPrice100());
		ann5.makeItaSearch();
		announcementRepository.saveAndFlush(ann1);
		announcementRepository.saveAndFlush(ann2);
		announcementRepository.saveAndFlush(ann3);
		announcementRepository.saveAndFlush(ann4);
		announcementRepository.saveAndFlush(ann5);

		// Add announcement in student list
		List<Announcement> listann = new ArrayList<>();
		listann.add(ann1);
		listann.add(ann2);
		listann.add(ann3);
		listann.add(ann4);
		listann.add(ann5);
		student.setAnnouncements(listann);

		// save student
		studentService.createOrUpdateStudent(student);

		User u = new User("user", "lastn", "usertest@t", "passtest");
		u.setStudentProfile(student);
		userService.createOrUpdateUser(u);

		userService.createAdmin("admin@t", "passadm");
	}

	private void createStudentTest(String lastname, String firstname, School school) {
		User user = new User(firstname, lastname, firstname + "." + lastname + "@lbce.com", "pass");
		Student student = new Student();
		student.setPhoneNumber("0123456789");
		student.setSchool(school);
		studentService.createOrUpdateStudent(student);
		user.setStudentProfile(student);
		userService.createOrUpdateUser(user);
	}

	private void createStudentMiageTest() {
		List<School> schools = schoolService.getAllSchools();
		int maxSchoolId = schools.size() - 1;

		Random random = new Random();
		createStudentTest("Bernard", "Alexandre", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Dechamps", "Aurore", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Pomme", "Thomas", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Aydogan", "Mehmet", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Rabadan", "Andy", schools.get(random.nextInt(maxSchoolId)));
		/*createStudentTest("Guery", "Steven", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Nagy", "Niko", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Silvestro", "Benoit", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Delas", "Romain", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Lantier", "Sebastien", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Ines", "Houara", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Chauvet", "Clemence", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Pinet", "Jeremy", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Pagh Birk", "Christian", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Spugna", "Lorris", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Barbe", "Brenda", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Rhazadi", "Walid", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Micaleff", "David", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Oualid", "Manai", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Nicoletti", "Sebastien", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Mota", "Thais", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Kaeffer", "Rémi", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Cochin", "Sacha", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Cojocaru", "Dragos", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Lamrani", "Rime", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Aglif", "Fatima", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Bellaiche", "Julian", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Bocarra", "David", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Constantin", "Yannis", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Deschanels", "Isaline", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Elsaesser", "Luc", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Maurisset", "Jason", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Abid", "Anna", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Diarra", "Ibrahima", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Chazarra", "Max", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Dupont", "Kenzo", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Azzaoui", "Reda", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Comba", "Florian", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Mvouma", "Michel", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Puybonnieux", "Pierre", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Rouis", "Myriam", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Eme", "Nag", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Begyn", "Mélissa", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Benyas", "Nour", schools.get(random.nextInt(maxSchoolId)));
		createStudentTest("Amira", "Mimouna", schools.get(random.nextInt(maxSchoolId)));*/
	}

	private void createCategories() {
		categoryService.createCategory(new Category("Offre d'emploi"));
		categoryService.createCategory(new Category("Voitures"));
		categoryService.createCategory(new Category("Motos"));
		categoryService.createCategory(new Category("Caravaning"));
		categoryService.createCategory(new Category("Utilitaires"));
		/*categoryService.createCategory(new Category("Equipement Auto"));
		categoryService.createCategory(new Category("Equipement Moto"));
		categoryService.createCategory(new Category("Equipement Caravaning"));
		categoryService.createCategory(new Category("Nautisme"));
		categoryService.createCategory(new Category("Equipement Nautisme"));
		categoryService.createCategory(new Category("Ventes immobilières"));
		categoryService.createCategory(new Category("Locations"));
		categoryService.createCategory(new Category("Colocations"));
		categoryService.createCategory(new Category("Chambres d'hôtes"));
		categoryService.createCategory(new Category("Campings"));
		categoryService.createCategory(new Category("Hôtels"));
		categoryService.createCategory(new Category("Hébergements insolites"));
		categoryService.createCategory(new Category("Informatique"));
		categoryService.createCategory(new Category("Consoles & Jeux vidéo"));
		categoryService.createCategory(new Category("Image & Son"));
		categoryService.createCategory(new Category("Téléphonie"));
		categoryService.createCategory(new Category("DVD / Films"));
		categoryService.createCategory(new Category("CD / Musique"));
		categoryService.createCategory(new Category("Livres"));
		categoryService.createCategory(new Category("Animaux"));
		categoryService.createCategory(new Category("Vélos"));
		categoryService.createCategory(new Category("Sports & Hobbies"));
		categoryService.createCategory(new Category("Instruments de musique"));
		categoryService.createCategory(new Category("Collection"));
		categoryService.createCategory(new Category("Jeux & Jouets"));
		categoryService.createCategory(new Category("Vins & Gastronomie"));
		categoryService.createCategory(new Category("Matériel Agricole"));
		categoryService.createCategory(new Category("Outillage"));
		categoryService.createCategory(new Category("Fournitures de Bureau"));
		categoryService.createCategory(new Category("Commerces & Marchés"));
		categoryService.createCategory(new Category("Matériel Médical"));
		categoryService.createCategory(new Category("Billetterie"));
		categoryService.createCategory(new Category("Evénements"));
		categoryService.createCategory(new Category("Cours particuliers"));
		categoryService.createCategory(new Category("Covoiturage"));
		categoryService.createCategory(new Category("Ameublement"));
		categoryService.createCategory(new Category("Electroménager"));
		categoryService.createCategory(new Category("Arts de la table"));
		categoryService.createCategory(new Category("Décoration"));
		categoryService.createCategory(new Category("Linge de maison"));
		categoryService.createCategory(new Category("Bricolage"));
		categoryService.createCategory(new Category("Jardinage"));
		categoryService.createCategory(new Category("Vêtements"));
		categoryService.createCategory(new Category("Chaussures"));
		categoryService.createCategory(new Category("Montres & Bijoux"));
		categoryService.createCategory(new Category("Equipement bébé"));
		categoryService.createCategory(new Category("Vêtements bébé"));
		categoryService.createCategory(new Category("Autres"));*/
	}

	private void createSchoolsTest() {
		// default school
//		schoolService.createSchool(new School("Nothing",new Address()));

		// other schools
		schoolService.createSchool(new School("Ecole nationale supérieure de la nature et du paysage", new Address("9 rue de la Chocolaterie", "41029", "Blois", "France", 1.3254551f, 47.5838942f), "193"));
		schoolService.createSchool(new School("IXAD Ecole des Avocats Nord-Ouest", new Address("1 place Déliot", "59024", "Lille", "France", 3.0686492f, 50.6197142f), "295"));
		schoolService.createSchool(new School("Institut national des techniques économiques et comptables", new Address("8 boulevard Louis XIV", "59024", "Lille", "France", 0.129004f, 49.4958179f), "603"));
		schoolService.createSchool(new School("Médiaquitaine - Centre régional de formation aux carrières des bibliothèques", new Address("10 A avenue d'Aquitaine", "33172", "Gradignan", "France", -0.5561339f, 44.8298632f), "1267"));
		schoolService.createSchool(new School("ESPÉ - École supérieure du professorat et de l'éducation Célestin Freinet (site de Nice, George V)", new Address("89 avenue Georges V", "06088", "Nice", "France", 5.890045f, 43.101422f), "5074"));
		/*schoolService.createSchool(new School("Institut supérieur d'économie et de management", new Address("24 avenue des Diables bleus", "06088", "Nice", "France", 7.2903508f, 43.7083101f), "23627"));
		schoolService.createSchool(new School("IUP Tourisme", new Address("Villa Arcadie", "06088", "Nice", "France", 7.2483479f, 43.6942128f), "22607"));
		schoolService.createSchool(new School("Faculté de lettres, arts et sciences humaines - Campus St Jean d'angely", new Address("24 avenue des Diables bleus", "06088", "Nice", "France", 7.2884677f, 43.7082791f), "22446"));
		schoolService.createSchool(new School("IUP Miage", new Address("1645 route des Lucioles", "06018", "Biot", "France", 7.0637145f, 43.6166598f), "22606"));
		schoolService.createSchool(new School("Faculté de chirurgie dentaire", new Address("24 avenue des Diables Bleus", "06088", "Nice", "France", 7.2884677f, 43.7082791f), "18060"));
		schoolService.createSchool(new School("Institut d'administration des entreprises", new Address("Pôle universitaire St Jean d'Angely", "06018", "Nice", "France", 7.2476801f, 43.6978459f), "16870"));
		schoolService.createSchool(new School("UFR3 de sciences humaines et sciences de l'environnement", new Address("Route de Mende", "34172", "Montpellier", "France", 3.8663644f, 43.6368173f), "16923"));
		schoolService.createSchool(new School("UFR de droit et science politique", new Address("39 rue de l'université", "34172", "Montpellier", "France", 3.8772542f, 43.614061f), "16973"));
		schoolService.createSchool(new School("UFR de lettres, langues et sciences humaines", new Address("29 boulevard Gergovia", "63113", "Clermont-Ferrand", "France", 3.0867279f, 45.7707363f), "17121"));
		schoolService.createSchool(new School("Ecole d'économie", new Address("41 boulevard François Mitterrand", "63113", "Clermont-Ferrand", "France", 3.0863503f, 45.7707643f), "17445"));
		schoolService.createSchool(new School("Faculté de médecine - Enseignements des techniques de réadaptation", new Address("133 route de Narbonne", "31555", "Toulouse", "France", 1.4650897f, 43.5571642f), "18004"));
		schoolService.createSchool(new School("UFR de physique et ingénierie", new Address("3-5 rue de l'Université", "67482", "Strasbourg", "France"), null));
	*/
	}

	public Category getRandomCategory() {
		int size = categoryService.getAllCategories().size();
		return categoryService.getAllCategories().get(Utils.getRandom(size));
	}
}
