package logic.plugin;

public final class NameGenerator {
    public static final NameGenerator instance = new NameGenerator();
    // TODO: load name from file
    private final String[] maleFirstname = {"Richard", "Roger", "Dinadan", "Alan", "Aldred", "Eluard", "Arnold", "Henry", "Basil", "Jocelyn", "Cyr", "Balin", "George", "Eliot", "Frederick", "Alexander", "Percival", "Anselm", "Mark", "David", "Albert", "Urian", "Tristram", "Paul", "Berenger", "Martin", "Merek", "Herman", "Hildebrand", "Edwin", "Gilbert", "Bliant", "Bennet", "Bryce", "Castor", "Giles", "Gunter", "Bernard", "Arthur", "Nigel", "Lucan", "Matthew", "Noah", "Lionel", "Bartholomew", "Bardolph", "Barnabas", "Bertram", "Wolfstan", "Hardwin", "Hamond", "Faramond", "Herbert", "Alisander", "Michael", "Milo", "Jordan", "Ulric", "Galleron", "Solomon", "Sampson", "Tobias", "Charles", "Diggory", "Drogo", "Hugh", "Baudwin", "Everard", "Nicholas", "Joseph", "Leofwin", "Amis", "Ranulf", "Fulke", "Theobald", "Rowan", "Geoffrey", "Gervase", "Gerard", "Godwyn", "Philip", "Warin", "Warner", "Thomas", "Brom", "Hamon", "Thurstan", "Robert", "Roland", "Rolf", "Walter", "Laurence", "Reginald", "Aglovale", "Sayer", "Timm", "Piers", "Cerdic", "Randel", "Daniel", "Denis", "Elias", "Gabriel", "Hector", "Humphrey", "Gamel", "Gregory", "James", "Jasper", "Jeremy", "Isaac", "Ingram", "Isembard", "Manfred", "Ives", "William", "Lucius", "Wymond", "Lambert", "Blaise", "Griffith", "Mabon", "Hubert", "Gerald", "Eustace", "Amaury", "Adam", "Adelard", "Alphonse", "Turstin", "Guy", "Peter", "Osric", "Ogier", "Gareth", "Maynard", "Miles"};
    private final String[] femaleFirstname = {"Elaine", "Sarah", "Sela", "Sigga", "Susanna", "Althea", "Alma", "Artemisia", "Anne", "Anais", "Acelina", "Aelina", "Aldith", "Audry", "Augusta", "Brangwine", "Bridget", "Genevieve", "Guinevere", "Catelin", "Cateline", "Caterina", "Dionisia", "Mary", "Molly", "Margaret", "Margery", "Martha", "Elizabeth", "Elysande", "Christina", "Giselle", "Regina", "Richolda", "Roana", "Barbetta", "Bertha", "Clare", "Clarice", "Amelina", "Cecily", "Edith", "Elle", "Juliana", "Ivette", "Adelina", "Agnes", "Alice", "Alyson", "Dameta", "Paulina", "Petronilla", "Edeva", "Eglenti", "Evelune", "Emeline", "Emily", "Emma", "Joan", "Johanna", "Lavena", "Lena", "Lovota", "Lillian", "Maude", "Milicent", "Magdalen", "Isabella", "Caesaria", "Mathilde", "Delia", "Sapphira", "Sophronia", "Tephania", "Theda", "Thora", "Odelina", "Oliva", "Orella", "Venetia", "Ysmeine", "Gracia", "Gratia", "Swanhild", "Sybil", "Mathild", "Ida", "Ingerid", "Isemay", "Celestria", "Constance", "Eleanor", "Amicia", "Avina", "Athelina", "Eva", "Gundred", "Felicia", "Floria", "Isolda", "Linota", "Cassandra", "Lucia", "Helewise", "Justina", "Joyce", "Joya", "Nesta", "Sabina", "Gisela", "Rosa", "Rosamund", "Evaine", "Viviane", "Laudine", "Letia", "Leticia", "Legarda", "Lia", "Lunete", "Florence", "Gwendolen", "Nicola", "Blanche", "Beatrice", "Marie", "Marion", "Miriel", "Mirielda", "Ingerith", "Maria", "Marian", "Muriel"};
    private final String[] lastname = {"Achard", "Ashdown", "Baker", "Baxter", "Burrel", "Capron", "Carter", "Custer", "Draper", "Danneville", "Mason", "Bouchard", "Ballard", "Bigge", "Brooker", "de Bethencourt", "le Orphelin", "de Logris", "de Maris", "Challenge", "le Blanc", "le Savage", "de la Porte", "de la Pole", "Clarke", "Cook", "Fletcher", "Bosc", "Brooker", "Browne", "Brickenden", "Cecil", "Capron", "Challener", "Griffen", "Canouville", "Beringar", "de Lacy", "Vaughan", "Fitzroy", "Forester", "de Balon", "Fuller", "Dyer", "Glanville", "Hayward", "de Servian", "Courcy", "d’Ambray", "Gaveston", "Bainard", "Bellecote", "Bertran", "Hachet", "Carpenter", "Cooper", "Foreman", "Hughes", "Duval", "Vernon", "Durville", "Deschamps", "Colleville", "de Challon", "de La Reue", "Bauldry", "Godart", "Manners", "de Ireby", "Webber", "Weaver", "Nash", "Auber", "Ward", "Wood", "Guideville", "Faintree", "Willoughby", "Saint-Germain", "Payne", "Renold", "Rolfe", "Cherbourg", "de Coucy", "Peveril", "Lister", "Parry", "Hood", "Medley", "Mercer", "Hauville", "le Roux", "Tolle", "Napier", "Taylor", "Teller", "Saint-Clair", "Lucy", "Osmont", "Mallory", "le Grant", "Lamb", "Latham", "Burroughs", "Nesdin", "Harcourt", "Alder", "Atwood", "de Montfort", "of Warwick", "Giffard", "Becker", "Paschal", "Perroy", "Lee", "Brewer", "Sawyer", "Shepherd", "Thibault", "Wright", "Barnes", "Butler", "Gurney", "Gael", "Gillian", "Roger", "Seller", "Thorne", "Basset", "FitzAlan", "Arundel", "Bolam", "Cardon", "Chauncy", "Darcy", "Slater", "Marshal", "Martel", "May", "Noyers", "Durandal", "Prestcote", "Godefroy", "Rainecourt", "Corbin", "Cumin", "Comyn", "Cardonell", "Perci", "Picard", "Clay", "Wells", "Evelyn", "Faucon", "Rames", "Reviers", "Mathan", "Corbet", "Court", "Emory", "Dumont", "Saint-Leger", "Vaux", "Verdun", "Cross", "Hendry", "Holland", "Destain", "Gilpin", "Mallory", "Parry", "Tilly", "Watteau", "Holmes", "la Mare", "le Blanc", "le Grant", "le Conte", "d’Albert", "de La Roche", "de Beauvais", "Wilde", "Crump", "Campion", "Beaumont", "Parmenter", "Papon", "Hope", "de Civille", "Achard", "Cambray", "Chauncy", "Fox", "Ide", "de Bethencourt", "de Blays", "Hawthorn", "Duval", "Dale", "Mathan", "Malet", "Mortmain", "Patris", "Port", "Drake", "of Cleremont", "of Benwick", "Dean", "de Erley", "des Roches", "Langdon", "de la Haye", "Lynom", "Gary", "Marchmain", "Mortimer", "Mowbray", "de Ferrers", "de Lorris", "de Grey", "Ford", "Dodd", "Raleigh", "of Wichelsea", "Grosseteste", "FitzOsbern", "de Grandmesnil", "deWarenne", "Writingham", "Rowntree", "Neuville", "Graves", "Mercier"};

    private NameGenerator() {
    }

    public static NameGenerator getInstance() {
        return instance;
    }

    public String getRandomName() {
        boolean isMale = Randomizer.getInstance().randomIntInRange(0, 1)==0;
        return getRandomName(isMale);
    }

    public String getRandomName(boolean isMale) {
        Randomizer r = Randomizer.getInstance();
        String result = "";
        if (isMale) {
            result += maleFirstname[r.randomIntInRange(0, maleFirstname.length-1)];
        } else {
            result += femaleFirstname[r.randomIntInRange(0, femaleFirstname.length-1)];
        }
        result += " " + lastname[r.randomIntInRange(0, lastname.length-1)];
        return result;
    }
}
