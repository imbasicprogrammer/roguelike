import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dungeons extends World
{
    private int currentWave = 0;
    private int enemiesRemaining = 0; // Melacak musuh yang hidup
    private int waveTimer = 180;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public  Dungeons()
    {    
        super(800, 600, 1); 
        prepare();
        spawnWave(1);
    }
    
    
    public void act()
    {
        // Cek jika semua musuh di gelombang saat ini sudah dikalahkan
        if (enemiesRemaining <= 0) 
        {
            // Tampilkan teks hitung mundur
            showText("Gelombang " + (currentWave + 1) + " akan datang...", 400, 30);
            
            waveTimer--; // Hitung mundur jeda
            
            // Jika waktu jeda habis, mulai gelombang berikutnya
            if (waveTimer <= 0) {
                currentWave++;                  // Naikkan level gelombang
                spawnWave(currentWave);         // Panggil gelombang baru
                waveTimer = 180;                // Reset jeda ke 3 detik
                showText("", 400, 30);      // Hapus teks hitung mundur
            }
        }
    }
    
    public void enemyDefeated() 
{
    enemiesRemaining--; // Kurangi jumlah musuh yang tersisa
    
    // Nanti Bos bisa tambahkan skor di sini
    // misal: score = score + 10;
}

private void spawnWave(int waveNumber) 
{
    showText("Gelombang: " + currentWave, 70, 30);
    
    if (waveNumber == 1) {
        // Gelombang 1: 3 Goblin
        enemiesRemaining = 3; // Kontrol jumlah
        spawnRandomEnemies(Vampire.class, 3); // Panggil helper
    }
    else if (waveNumber == 2) {
        // Gelombang 2: 5 Goblin
        enemiesRemaining = 5; // Kontrol jumlah
        spawnRandomEnemies(Vampire.class, 5); // Panggil helper
    }
    else if (waveNumber == 3) {
        // Gelombang 3: 4 Goblin dan 2 Skeleton
        // (Asumsi Bos sudah buat Skeleton.java)
        
        enemiesRemaining = 6; // Kontrol jumlah (4 + 2)
        spawnRandomEnemies(Vampire.class, 4); // Panggil helper untuk 4 Goblin
        // spawnRandomEnemies(Skeleton.class, 2); // Panggil helper untuk 2 Skeleton
    }
    else {
        // Gelombang terakhir
        showText("ANDA MENANG!", 400, 300);
        Greenfoot.stop();
    }
}

private <T extends Character> void spawnRandomEnemies(Class<T> enemyType, int count) 
    {
        int spawned = 0; // Hitungan musuh yang sudah berhasil spawn
        int worldWidth = getWidth();
        int worldHeight = getHeight();
        
        // Dapatkan referensi Player agar kita tidak spawn di atasnya
        player player = null; // (Ganti 'player' ke 'Player' jika perlu)
        if (!getObjects(player.class).isEmpty()) {
            player = getObjects(player.class).get(0);
        }

        // Terus coba sampai jumlah 'spawned' sesuai 'count'
        while (spawned < count) 
        {
            // 1. Dapatkan koordinat X dan Y acak
            int x = Greenfoot.getRandomNumber(worldWidth);
            int y = Greenfoot.getRandomNumber(worldHeight);

            // 2. Lakukan Pengecekan Keamanan
            
            // Cek 2a: Apakah titik (x,y) ada di dalam Tembok?
            if (!getObjectsAt(x, y, Wall.class).isEmpty()) {
                continue; // Coba lagi, lokasi ini ada tembok
            }
            
            // Cek 2b: Apakah terlalu dekat dengan Player? (Jarak aman 150px)
            if (player != null && Math.abs(player.getX() - x) < 150 && Math.abs(player.getY() - y) < 150) {
                continue; // Coba lagi, terlalu dekat player
            }
            
            // Cek 2c: Apakah ada musuh lain di titik ini? (Opsional)
            if (!getObjectsAt(x, y, Character.class).isEmpty()) { // Cek 'Character' agar tidak tumpuk
                continue; // Coba lagi, sudah ada karakter lain di sini
            }

            // --- LOKASI AMAN! ---
            
            // 3. Buat dan tambahkan musuh ke dunia
            try {
                // Ini cara canggih untuk bilang "new Goblin()" atau "new Skeleton()"
                Character newEnemy = enemyType.getDeclaredConstructor().newInstance();
                addObject(newEnemy, x, y);
                spawned++; // Berhasil spawn 1, lanjut ke berikutnya
            } 
            catch (Exception e) {
                e.printStackTrace();
                break; // Hentikan loop jika ada error
            }
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {

        player knight = new Villager();
        addObject(knight,113,60);

        Wall wall = new Wall();
        addObject(wall,512,11);
        Wall wall2 = new Wall();
        addObject(wall2,512,33);
        wall2.setLocation(512,37);
        Wall wall3 = new Wall();
        addObject(wall3,512,63);
        Wall wall4 = new Wall();
        addObject(wall4,512,89);
        Wall wall5 = new Wall();
        addObject(wall5,512,115);
        Wall wall6 = new Wall();
        addObject(wall6,512,139);
        Wall wall7 = new Wall();
        addObject(wall7,512,163);
        Wall wall8 = new Wall();
        addObject(wall8,512,186);
        Wall wall9 = new Wall();
        addObject(wall9,512,212);
        Wall wall10 = new Wall();
        addObject(wall10,512,238);
        Wall wall11 = new Wall();
        addObject(wall11,511,264);
        Wall wall12 = new Wall();
        addObject(wall12,511,288);
        Wall wall13 = new Wall();
        addObject(wall13,534,288);
        Wall wall14 = new Wall();
        addObject(wall14,556,288);
        Wall wall15 = new Wall();
        addObject(wall15,581,288);
        Wall wall16 = new Wall();
        addObject(wall16,788,288);
        Wall wall17 = new Wall();
        addObject(wall17,762,287);
        Wall wall18 = new Wall();
        addObject(wall18,736,288);
        Wall wall19 = new Wall();
        addObject(wall19,710,287);
        Wall wall20 = new Wall();
        addObject(wall20,788,587);
        wall20.setLocation(784,586);
        Wall wall21 = new Wall();
        addObject(wall21,783,560);
        Wall wall22 = new Wall();
        addObject(wall22,783,533);
        Wall wall23 = new Wall();
        addObject(wall23,783,508);
        Wall wall24 = new Wall();
        addObject(wall24,782,481);
        Wall wall25 = new Wall();
        addObject(wall25,781,453);
        Wall wall26 = new Wall();
        addObject(wall26,755,453);
        Wall wall27 = new Wall();
        addObject(wall27,728,452);
        Wall wall28 = new Wall();
        addObject(wall28,702,451);
        Wall wall29 = new Wall();
        addObject(wall29,675,451);
        Wall wall30 = new Wall();
        addObject(wall30,674,478);
        Wall wall31 = new Wall();
        addObject(wall31,674,504);
        Wall wall32 = new Wall();
        addObject(wall32,673,529);
        Wall wall33 = new Wall();
        addObject(wall33,673,556);
        Wall wall34 = new Wall();
        addObject(wall34,672,583);
        Wall wall35 = new Wall();
        addObject(wall35,694,583);
        Wall wall36 = new Wall();
        addObject(wall36,716,582);
        Wall wall37 = new Wall();
        addObject(wall37,739,584);
        Wall wall38 = new Wall();
        addObject(wall38,761,583);
        Wall wall39 = new Wall();
        addObject(wall39,276,583);
        Wall wall40 = new Wall();
        addObject(wall40,276,556);
        Wall wall41 = new Wall();
        addObject(wall41,276,529);
        Wall wall42 = new Wall();
        addObject(wall42,276,502);
        Wall wall43 = new Wall();
        addObject(wall43,276,475);
        Wall wall44 = new Wall();
        addObject(wall44,275,448);
        Wall wall45 = new Wall();
        addObject(wall45,274,421);
        Wall wall46 = new Wall();
        addObject(wall46,249,421);
        Wall wall47 = new Wall();
        addObject(wall47,223,420);
        Wall wall48 = new Wall();
        addObject(wall48,199,421);
        Wall wall49 = new Wall();
        addObject(wall49,175,421);
        Wall wall50 = new Wall();
        addObject(wall50,149,420);
        Wall wall51 = new Wall();
        addObject(wall51,123,421);
        Wall wall52 = new Wall();
        addObject(wall52,122,447);
        Wall wall53 = new Wall();
        addObject(wall53,123,473);
        Wall wall54 = new Wall();
        addObject(wall54,123,499);
        Wall wall55 = new Wall();
        addObject(wall55,146,500);
        Wall wall56 = new Wall();
        addObject(wall56,169,499);
        Wall wall57 = new Wall();
        addObject(wall57,195,11);
        Wall wall58 = new Wall();
        addObject(wall58,220,12);
        Wall wall59 = new Wall();
        addObject(wall59,245,12);
        Wall wall60 = new Wall();
        addObject(wall60,268,12);
        Wall wall61 = new Wall();
        addObject(wall61,291,12);
        Wall wall62 = new Wall();
        addObject(wall62,315,12);
        Wall wall63 = new Wall();
        addObject(wall63,339,12);
        Wall wall64 = new Wall();
        addObject(wall64,362,13);
        Wall wall65 = new Wall();
        addObject(wall65,363,37);
        wall65.setLocation(360,37);
        wall65.setLocation(364,37);
        Wall wall66 = new Wall();
        addObject(wall66,363,64);
        Wall wall67 = new Wall();
        addObject(wall67,363,91);
        Wall wall68 = new Wall();
        addObject(wall68,363,118);
        Wall wall69 = new Wall();
        addObject(wall69,337,118);
        Wall wall70 = new Wall();
        addObject(wall70,315,119);
        Wall wall72 = new Wall();
        addObject(wall72,247,111);
        Wall wall73 = new Wall();
        addObject(wall73,222,112);
        Wall wall74 = new Wall();
        addObject(wall74,197,111);
        Wall wall75 = new Wall();
        addObject(wall75,197,87);
        Wall wall76 = new Wall();
        addObject(wall76,196,63);
        Wall wall77 = new Wall();
        addObject(wall77,196,38);

        Wall wall78 = new Wall();
        addObject(wall78,10,234);
        Wall wall79 = new Wall();
        addObject(wall79,34,234);
        Wall wall80 = new Wall();
        addObject(wall80,57,234);
        Wall wall81 = new Wall();
        addObject(wall81,81,233);
        Wall wall82 = new Wall();
        addObject(wall82,105,235);
        Wall wall83 = new Wall();
        addObject(wall83,127,235);
        Wall wall84 = new Wall();
        addObject(wall84,150,235);
        Wall wall85 = new Wall();
        addObject(wall85,173,235);
        Wall wall86 = new Wall();
        addObject(wall86,195,235);
        Wall wall87 = new Wall();
        addObject(wall87,217,235);
        Wall wall88 = new Wall();
        addObject(wall88,239,234);
        Wall wall89 = new Wall();
        addObject(wall89,262,233);
        Wall wall90 = new Wall();
        addObject(wall90,286,233);
        Wall wall91 = new Wall();
        addObject(wall91,284,258);
        Wall wall92 = new Wall();
        addObject(wall92,284,284);
        Wall wall93 = new Wall();
        addObject(wall93,259,285);
        Wall wall94 = new Wall();
        addObject(wall94,234,284);
        Wall wall95 = new Wall();
        addObject(wall95,208,285);
        Wall wall96 = new Wall();
        addObject(wall96,183,286);
        Wall wall97 = new Wall();
        addObject(wall97,159,284);
        Wall wall98 = new Wall();
        addObject(wall98,135,284);
        Wall wall99 = new Wall();
        addObject(wall99,109,284);
        Wall wall100 = new Wall();
        addObject(wall100,86,284);
        Wall wall101 = new Wall();
        addObject(wall101,60,284);
        Wall wall102 = new Wall();
        addObject(wall102,35,283);
        Wall wall103 = new Wall();
        addObject(wall103,13,283);
    
       
    }
    
    
}
