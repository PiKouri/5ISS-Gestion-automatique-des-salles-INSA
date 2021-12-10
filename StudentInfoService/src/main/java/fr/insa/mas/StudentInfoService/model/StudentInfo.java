package fr.insa.mas.StudentInfoService.model;

public class StudentInfo {
	
	private static int compteur = 0;
	private int id;
	private String birthDate;
	private String nom;
	private String prenom;
	
	
	public StudentInfo(String birthDate, String nom, String prenom) {
		this.id = compteur;
		compteur = compteur + 1;
		this.birthDate = birthDate;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public StudentInfo() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}	

}
