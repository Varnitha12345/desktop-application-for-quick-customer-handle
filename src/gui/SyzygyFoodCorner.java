package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Order {

    private final String food;
    private final String ingredient;
    private final String subMenu;
    private final String foodprice;

    private Order(Order.Builder builder) {
        this.food = builder.food;
        this.ingredient = builder.ingredient;
        this.subMenu = builder.subMenu;
        this.foodprice = builder.foodprice;
    }

    public String getFood() {
        return food;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public String getFoodprice() {
        return foodprice;
    }

    @Override
    public String toString() {
        return getFood() + "(" + getIngredient() + ") " + getSubMenu();
    }

    static class Builder {

        private String food;
        private String ingredient;
        private String subMenu;
        private String foodprice;

        public Builder setFood(String food) {
            this.food = food;
            return this;
        }

        public Builder setIngredient(String ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder setSubMenu(String subMenu) {
            this.subMenu = subMenu;
            return this;
        }

        public Builder setFoodprice(String foodprice) {
            this.foodprice = foodprice;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}

interface Expression {

    public int Interpret();

}

//Number
class TerminalExpression implements Expression {

    int value;

    public TerminalExpression(int value) {
        this.value = value;
    }

    @Override
    public int Interpret() {
        return this.value;
    }

}

//+
class NonTerminalExpression implements Expression {

    Expression expression1;
    Expression expression2;

    public NonTerminalExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int Interpret() {
        return expression1.Interpret() + expression2.Interpret();
    }

}

class Ingredient {

    protected SyzygyFoodCorner.FoodMediator foodMediator;

    public Ingredient() {
    }
    private static final HashMap<String, Ingredient> POOL = new HashMap<>();

    public static Ingredient createInstance(String chilli, String salt, String curry) {

        Ingredient instance = POOL.get("chilli=" + chilli + "," + "salt=" + salt + "," + "curry=" + curry);

        if (instance == null) {
            instance = new Ingredient();
            instance.chilli = chilli;
            instance.salt = salt;
            instance.curry = curry;
            POOL.put("chilli=" + chilli + "," + "salt=" + salt + "," + "curry=" + curry, instance);
        }

        return instance;

    }

    private String chilli;
    private String salt;
    private String curry;

    public String getChilli() {
        return chilli;
    }

    public String getSalt() {
        return salt;
    }

    public String getCurry() {
        return curry;
    }

    public void setFoodMediator(SyzygyFoodCorner.FoodMediator foodMediator) {
        this.foodMediator = foodMediator;
    }
}

public class SyzygyFoodCorner extends javax.swing.JFrame {

    private static String foodDescription = "";
    private static String foodPrice = "";
    private static int FinalPrice = 0;

    public SyzygyFoodCorner() {
        initComponents();
    }

    abstract class Food {

        protected FoodMediator foodMediator;

        public abstract String getDescription();

        public abstract String getPrice();

        public void setFoodMediator(FoodMediator foodMediator) {
            this.foodMediator = foodMediator;
        }
    }

    class FriedRice extends Food {

        @Override
        public String getDescription() {
            return "Fried Rice";
        }

        @Override
        public String getPrice() {
            return "650";
        }
    }

    class Koththu extends Food {

        @Override
        public String getDescription() {
            return "Koththu";
        }

        @Override
        public String getPrice() {
            return "600";
        }
    }

    class NasiGoreng extends Food {

        @Override
        public String getDescription() {
            return "Nasi Goreng";
        }

        @Override
        public String getPrice() {
            return "750";
        }
    }

    class ParataPotion extends Food {

        @Override
        public String getDescription() {
            return "Parata Potion(10)";
        }

        @Override
        public String getPrice() {
            return "400";
        }
    }

    class RiceAndCurry extends Food {

        @Override
        public String getDescription() {
            return "Rice & Curry";
        }

        @Override
        public String getPrice() {
            return "300";
        }
    }

    class Ingredients extends Ingredient {

        String allIngredients = "";

        public String getDescription() {
            if (jComboBox2.getSelectedIndex() == 1) {
                allIngredients += "";
            } else {
                allIngredients += jComboBox2.getSelectedItem().toString() + "-chilli ";
            }
            if (jComboBox3.getSelectedIndex() == 1) {
                allIngredients += "";
            } else {
                allIngredients += jComboBox3.getSelectedItem().toString() + "-salt ";
            }
            if (jComboBox4.getSelectedIndex() == 1) {
                allIngredients += "";
            } else {
                allIngredients += jComboBox4.getSelectedItem().toString() + "-curry ";
            }
            return allIngredients;
        }

    }

    abstract class ExtraIngredient {

        protected FoodMediator foodMediator;

        public abstract String getDescription();

        public abstract String getPrice();

        public void setFoodMediator(FoodMediator foodMediator) {
            this.foodMediator = foodMediator;
        }
    }

    class Veg extends ExtraIngredient {

        @Override
        public String getDescription() {
            return "(Veg)";
        }

        @Override
        public String getPrice() {
            return "+0";
        }
    }

    class Egg extends ExtraIngredient {

        @Override
        public String getDescription() {
            return "(Egg)";
        }

        @Override
        public String getPrice() {
            return "+60";
        }
    }

    class Fish extends ExtraIngredient {

        @Override
        public String getDescription() {
            return "(Fish)";
        }

        @Override
        public String getPrice() {
            return "+100";
        }
    }

    class Chicken extends ExtraIngredient {

        @Override
        public String getDescription() {
            return "(Chicken)";
        }

        @Override
        public String getPrice() {
            return "+80";
        }
    }

    abstract class ExtraSubMenu {

        protected FoodMediator foodMediator;

        public abstract String getDescription();

        public abstract String getPrice();

        public void setFoodMediator(FoodMediator foodMediator) {
            this.foodMediator = foodMediator;
        }
    }

    class CreamyCheese extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Creamy cheese";
        }

        @Override
        public String getPrice() {
            return "+750";
        }
    }

    class ChickenCurry extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Chicken curry";
        }

        @Override
        public String getPrice() {
            return "+400";
        }
    }

    class FishCurry extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Fish curry";
        }

        @Override
        public String getPrice() {
            return "+300";
        }
    }

    class OmletEgg extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Omlet(Egg)";
        }

        @Override
        public String getPrice() {
            return "+350";
        }
    }

    class ChickenDevel extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Chicken devel";
        }

        @Override
        public String getPrice() {
            return "+650";
        }
    }

    class MushroomDevel extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Mushroom devel";
        }

        @Override
        public String getPrice() {
            return "+750";
        }
    }

    class FishPuffs extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Fish puffs";
        }

        @Override
        public String getPrice() {
            return "+600";
        }
    }

    class ChopSuey extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ ChopSuey";
        }

        @Override
        public String getPrice() {
            return "+550";
        }
    }

    class ChilliPaste extends ExtraSubMenu {

        @Override
        public String getDescription() {
            return "/ Chilli paste";
        }

        @Override
        public String getPrice() {
            return "+150";
        }
    }

    class FoodMediator {

        private final FriedRice friedRice;
        private final Koththu koththu;
        private final NasiGoreng nasiGoreng;
        private final ParataPotion parataPotion;
        private final RiceAndCurry riceAndCurry;
        private final Ingredients ingredients;
        private final Veg veg;
        private final Egg egg;
        private final Fish fish;
        private final Chicken chicken;
        private final CreamyCheese creamyCheese;
        private final ChickenCurry chickenCurry;
        private final FishCurry fishCurry;
        private final OmletEgg omletEgg;
        private final ChickenDevel chickenDevel;
        private final MushroomDevel mushroomDevel;
        private final FishPuffs fishPuffs;
        private final ChopSuey chopSuey;
        private final ChilliPaste chilliPaste;

        public FoodMediator(FriedRice friedRice, Koththu koththu, NasiGoreng nasiGoreng, ParataPotion parataPotion, RiceAndCurry riceAndCurry, Ingredients ingredients, Veg veg, Egg egg, Fish fish, Chicken chicken, CreamyCheese creamyCheese, ChickenCurry chickenCurry, FishCurry fishCurry, OmletEgg omletEgg, ChickenDevel chickenDevel, MushroomDevel mushroomDevel, FishPuffs fishPuffs, ChopSuey chopSuey, ChilliPaste chilliPaste) {
            this.friedRice = friedRice;
            this.koththu = koththu;
            this.nasiGoreng = nasiGoreng;
            this.parataPotion = parataPotion;
            this.riceAndCurry = riceAndCurry;
            this.ingredients = ingredients;
            this.veg = veg;
            this.egg = egg;
            this.fish = fish;
            this.chicken = chicken;
            this.creamyCheese = creamyCheese;
            this.chickenCurry = chickenCurry;
            this.fishCurry = fishCurry;
            this.omletEgg = omletEgg;
            this.chickenDevel = chickenDevel;
            this.mushroomDevel = mushroomDevel;
            this.fishPuffs = fishPuffs;
            this.chopSuey = chopSuey;
            this.chilliPaste = chilliPaste;
        }

        public String callFriedRice() {
            return friedRice.getDescription();
        }

        public String callFriedRiceP() {
            return friedRice.getPrice();
        }

        public String callKoththu() {
            return koththu.getDescription();
        }

        public String callKoththuP() {
            return koththu.getPrice();
        }

        public String callNasiGoreng() {
            return nasiGoreng.getDescription();
        }

        public String callNasiGorengP() {
            return nasiGoreng.getPrice();
        }

        public String callParataPotion() {
            return parataPotion.getDescription();
        }

        public String callParataPotionP() {
            return parataPotion.getPrice();
        }

        public String callRiceAndCurry() {
            return riceAndCurry.getDescription();
        }

        public String callRiceAndCurryP() {
            return riceAndCurry.getPrice();
        }

        public String callIngredients() {
            return ingredients.getDescription();
        }

        public String callVeg() {
            return veg.getDescription();
        }

        public String callVegP() {
            return veg.getPrice();
        }

        public String callEgg() {
            return egg.getDescription();
        }

        public String callEggP() {
            return egg.getPrice();
        }

        public String callFish() {
            return fish.getDescription();
        }

        public String callFishP() {
            return fish.getPrice();
        }

        public String callChicken() {
            return chicken.getDescription();
        }

        public String callChickenP() {
            return chicken.getPrice();
        }

        public String callCreamyCheese() {
            return creamyCheese.getDescription();
        }

        public String callCreamyCheeseP() {
            return creamyCheese.getPrice();
        }

        public String callChickenCurry() {
            return chickenCurry.getDescription();
        }

        public String callChickenCurryP() {
            return chickenCurry.getPrice();
        }

        public String callFishCurry() {
            return fishCurry.getDescription();
        }

        public String callFishCurryP() {
            return fishCurry.getPrice();
        }

        public String callOmletEgg() {
            return omletEgg.getDescription();
        }

        public String callOmletEggP() {
            return omletEgg.getPrice();
        }

        public String callChickenDevel() {
            return chickenDevel.getDescription();
        }

        public String callChickenDevelP() {
            return chickenDevel.getPrice();
        }

        public String callMushroomDevel() {
            return mushroomDevel.getDescription();
        }

        public String callMushroomDevelP() {
            return mushroomDevel.getPrice();
        }

        public String callFishPuffs() {
            return fishPuffs.getDescription();
        }

        public String callFishPuffsP() {
            return fishPuffs.getPrice();
        }

        public String callChopSuey() {
            return chopSuey.getDescription();
        }

        public String callChopSueyP() {
            return chopSuey.getPrice();
        }

        public String callChilliPaste() {
            return chilliPaste.getDescription();
        }

        public String callChilliPasteP() {
            return chilliPaste.getPrice();
        }
    }

    private void resetMenu() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(1);
        jComboBox3.setSelectedIndex(1);
        jComboBox4.setSelectedIndex(1);
        jComboBox5.setSelectedIndex(1);
        jComboBox6.setSelectedIndex(1);
        jComboBox7.setSelectedIndex(1);
        jComboBox8.setSelectedIndex(1);
        jComboBox9.setSelectedIndex(1);
        jComboBox10.setSelectedIndex(1);
        jComboBox11.setSelectedIndex(1);
        jComboBox12.setSelectedIndex(1);
        jComboBox13.setSelectedIndex(1);
        jCheckBox1.setSelected(true);
        jCheckBox3.setSelected(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FoodCategory = new javax.swing.ButtonGroup();
        SupplyType = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HOME");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SYZYGY FOOD CORNER");

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fried.jpeg"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Elephant", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Food Menus :");

        jComboBox1.setBackground(new java.awt.Color(204, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fried Rice", "Koththu", "Nasi Goreng", "Parata Potion", "Rice & Curry", " " }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel5.setFont(new java.awt.Font("Elephant", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ingredients");

        jPanel6.setOpaque(false);

        FoodCategory.add(jCheckBox3);
        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Vegetable");
        jCheckBox3.setOpaque(false);
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        FoodCategory.add(jCheckBox4);
        jCheckBox4.setText("Egg");
        jCheckBox4.setOpaque(false);

        FoodCategory.add(jCheckBox5);
        jCheckBox5.setText("Fish");
        jCheckBox5.setOpaque(false);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        FoodCategory.add(jCheckBox6);
        jCheckBox6.setText("Chicken");
        jCheckBox6.setOpaque(false);
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Chilly Level  :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Normal", "High", "Extra High" }));
        jComboBox2.setSelectedIndex(1);

        jLabel6.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Salt Level  :");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Normal", "High", "Extra High" }));
        jComboBox3.setSelectedIndex(1);

        jLabel7.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Curry Level  :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Normal", "High", "Extra High" }));
        jComboBox4.setSelectedIndex(1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        jLabel9.setFont(new java.awt.Font("Elephant", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Sub Menu");

        jLabel10.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Creamy Cheese  :");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox5.setSelectedIndex(1);

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox7.setSelectedIndex(1);

        jLabel12.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Fish Curry  :");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox6.setSelectedIndex(1);

        jLabel11.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Chicken Curry  :");

        jLabel13.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Omlet (Egg) :");

        jLabel14.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Chicken Devel  :");

        jLabel15.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Mushroom Devel  :");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox9.setSelectedIndex(1);

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox10.setSelectedIndex(1);

        jLabel16.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Fish Puffs :");

        jLabel17.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("ChopSuey  :");

        jLabel18.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Chilli Paste  :");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox12.setSelectedIndex(1);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox8.setSelectedIndex(1);

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox11.setSelectedIndex(1);

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox13.setSelectedIndex(1);
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox12, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox11, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox13, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jComboBox12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jComboBox13))
                .addContainerGap())
        );

        jButton1.setBackground(new java.awt.Color(0, 255, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("ORDER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 255));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        SupplyType.add(jCheckBox1);
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Take Away");
        jCheckBox1.setOpaque(false);

        SupplyType.add(jCheckBox2);
        jCheckBox2.setText("Dine In");
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton3.setBackground(new java.awt.Color(153, 153, 255));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Clear Order");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        class Customer {

            String order;
            boolean ordertype;

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public boolean isOrdertype() {
                return ordertype;
            }

            public void setOrdertype(boolean ordertype) {
                this.ordertype = ordertype;
            }

        }

        abstract class ProcessController {

            protected ProcessController processController;

            public void setProcessController(ProcessController processController) {
                this.processController = processController;
            }

            public abstract void process(Customer customer);
        }

        class Accepting extends ProcessController {

            @Override
            public void process(Customer customer) {
                if (!jTextArea1.getText().isEmpty()) {
                    if (customer.isOrdertype()) {
                        JFrame frame = new JFrame();
                        String message = "Is the Payment Confirmed ?";
                        int answer = JOptionPane.showConfirmDialog(frame, message);
                        if (answer == JOptionPane.YES_OPTION) {
                            processController.process(customer);
                        } else if (answer == JOptionPane.NO_OPTION) {
                            frame.dispose();
                        }
                    } else {
                        processController.process(customer);
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Please add any order first", "WARNING", JOptionPane.WARNING_MESSAGE);
                }

            }
        }

        class Cooking extends ProcessController {

            @Override
            public void process(Customer customer) {
                if (customer.isOrdertype()) {
                    CookingP cp = new CookingP(SyzygyFoodCorner.this, true);
                    cp.setVisible(true);
                    if (cp.isVisible()) {
                        System.out.println("Processing Cooking(" + customer.order + ")");
                    } else {
                        processController.process(customer);
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Cookng process in manually according to the crowd", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }

        }

        class Packing extends ProcessController {

            @Override
            public void process(Customer customer) {
                if (customer.isOrdertype()) {
                    PackingP pp = new PackingP(SyzygyFoodCorner.this, true);
                    pp.setVisible(true);
                    if (pp.isVisible()) {
                        System.out.println("Processing Packing(" + customer.order + ")");
                    } else {
                        processController.process(customer);
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Packing process is not allowed in dine-in selection", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        class Handover extends ProcessController {

            @Override
            public void process(Customer customer) {
                JFrame frame = new JFrame();
                if (customer.isOrdertype()) {
                    JOptionPane.showMessageDialog(frame, "Order is placed Successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "take the payment", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                if(frame.isVisible()){
                    resetMenu();
                }else{
                    resetMenu();
                }
            }
        }

        Customer customer = new Customer();
        customer.setOrder(jTextArea1.getText());
        customer.setOrdertype(SupplyType.isSelected(jCheckBox1.getModel()));

        Accepting accepting = new Accepting();
        Cooking cooking = new Cooking();
        Packing packing = new Packing();
        Handover handover = new Handover();

        accepting.setProcessController(cooking);
        cooking.setProcessController(packing);
        packing.setProcessController(handover);
        accepting.process(customer);

        resetMenu();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        int foodItem = jComboBox1.getSelectedIndex();

        if (foodItem == 2) {
            jPanel3.setVisible(true);
            jPanel5.setVisible(true);
            jPanel6.setVisible(false);
        } else if (foodItem == 3) {
            jPanel5.setVisible(true);
            jPanel6.setVisible(true);
            jPanel3.setVisible(false);
        } else if (foodItem == 4) {
            jPanel3.setVisible(true);
            jPanel6.setVisible(true);
            jPanel5.setVisible(false);
        } else {
            jPanel3.setVisible(true);
            jPanel5.setVisible(true);
            jPanel6.setVisible(true);
        }

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String Extraingredients = "";
        String AllIngredients = "";
        String submenus = "";
        String ingredientsP = "";
        String supplyType = "";

        Ingredient ingredientNormal = Ingredient.createInstance("Normal", "Normal", "Normal");

        Order.Builder builder1 = new Order.Builder();

        FriedRice friedRice = new FriedRice();
        Koththu koththu = new Koththu();
        NasiGoreng nasiGoreng = new NasiGoreng();
        ParataPotion parataPotion = new ParataPotion();
        RiceAndCurry riceAndCurry = new RiceAndCurry();
        Ingredients ingredients = new Ingredients();
        Veg veg = new Veg();
        Egg egg = new Egg();
        Fish fish = new Fish();
        Chicken chicken = new Chicken();
        CreamyCheese creamyCheese = new CreamyCheese();
        ChickenCurry chickenCurry = new ChickenCurry();
        FishCurry fishCurry = new FishCurry();
        OmletEgg omletEgg = new OmletEgg();
        ChickenDevel chickenDevel = new ChickenDevel();
        MushroomDevel mushroomDevel = new MushroomDevel();
        FishPuffs fishPuffs = new FishPuffs();
        ChopSuey chopSuey = new ChopSuey();
        ChilliPaste chilliPaste = new ChilliPaste();

        FoodMediator foodMediator = new FoodMediator(friedRice, koththu, nasiGoreng, parataPotion, riceAndCurry, ingredients, veg, egg, fish, chicken, creamyCheese, chickenCurry, fishCurry, omletEgg, chickenDevel, mushroomDevel, fishPuffs, chopSuey, chilliPaste);

        friedRice.setFoodMediator(foodMediator);
        koththu.setFoodMediator(foodMediator);
        nasiGoreng.setFoodMediator(foodMediator);
        parataPotion.setFoodMediator(foodMediator);
        riceAndCurry.setFoodMediator(foodMediator);
        ingredients.setFoodMediator(foodMediator);
        veg.setFoodMediator(foodMediator);
        egg.setFoodMediator(foodMediator);
        fish.setFoodMediator(foodMediator);
        chicken.setFoodMediator(foodMediator);
        creamyCheese.setFoodMediator(foodMediator);
        chickenCurry.setFoodMediator(foodMediator);
        fishCurry.setFoodMediator(foodMediator);
        omletEgg.setFoodMediator(foodMediator);
        chickenDevel.setFoodMediator(foodMediator);
        mushroomDevel.setFoodMediator(foodMediator);
        fishPuffs.setFoodMediator(foodMediator);
        chopSuey.setFoodMediator(foodMediator);
        chilliPaste.setFoodMediator(foodMediator);

        builder1.setFood(jComboBox1.getSelectedItem().toString());

        switch (jComboBox1.getSelectedIndex()) {
            case 0:
                ingredientsP += foodMediator.callFriedRiceP();
                if (!jComboBox2.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else if (!jComboBox3.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else if (!jComboBox4.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else {
                    Ingredient ingredientNew = Ingredient.createInstance(jComboBox2.getSelectedItem().toString(), jComboBox3.getSelectedItem().toString(), jComboBox4.getSelectedItem().toString());
                    if (ingredientNormal == ingredientNew) {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are added", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are Changed", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                    AllIngredients += "";
                }
                if (FoodCategory.isSelected(jCheckBox3.getModel())) {
                    Extraingredients += foodMediator.callVeg();
                    ingredientsP += foodMediator.callVegP();
                } else if (FoodCategory.isSelected(jCheckBox4.getModel())) {
                    Extraingredients += foodMediator.callEgg();
                    ingredientsP += foodMediator.callEggP();
                } else if (FoodCategory.isSelected(jCheckBox5.getModel())) {
                    Extraingredients += foodMediator.callFish();
                    ingredientsP += foodMediator.callFishP();
                } else if (FoodCategory.isSelected(jCheckBox6.getModel())) {
                    Extraingredients += foodMediator.callChicken();
                    ingredientsP += foodMediator.callChickenP();
                } else {
                    Extraingredients += "";
                    ingredientsP += "";
                }
                if (jComboBox5.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callCreamyCheese();
                    ingredientsP += foodMediator.callCreamyCheeseP();
                }
                if (jComboBox6.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenCurry();
                    ingredientsP += foodMediator.callChickenCurryP();
                }
                if (jComboBox7.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishCurry();
                    ingredientsP += foodMediator.callFishCurryP();
                }
                if (jComboBox8.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callOmletEgg();
                    ingredientsP += foodMediator.callOmletEggP();
                }
                if (jComboBox9.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenDevel();
                    ingredientsP += foodMediator.callChickenDevelP();
                }
                if (jComboBox10.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callMushroomDevel();
                    ingredientsP += foodMediator.callMushroomDevelP();
                }
                if (jComboBox11.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishPuffs();
                    ingredientsP += foodMediator.callFishPuffsP();
                }
                if (jComboBox12.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChopSuey();
                    ingredientsP += foodMediator.callChopSueyP();
                }
                if (jComboBox13.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChilliPaste();
                    ingredientsP += foodMediator.callChilliPasteP();
                }
                break;
            case 1:
                ingredientsP += foodMediator.callKoththuP();
                if (!jComboBox2.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else if (!jComboBox3.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else if (!jComboBox4.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else {
                    Ingredient ingredientNew = Ingredient.createInstance(jComboBox2.getSelectedItem().toString(), jComboBox3.getSelectedItem().toString(), jComboBox4.getSelectedItem().toString());
                    if (ingredientNormal == ingredientNew) {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are added", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are Changed", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                    AllIngredients += "";
                }
                if (FoodCategory.isSelected(jCheckBox3.getModel())) {
                    Extraingredients += foodMediator.callVeg();
                    ingredientsP += foodMediator.callVegP();
                } else if (FoodCategory.isSelected(jCheckBox4.getModel())) {
                    Extraingredients += foodMediator.callEgg();
                    ingredientsP += foodMediator.callEggP();
                } else if (FoodCategory.isSelected(jCheckBox5.getModel())) {
                    Extraingredients += foodMediator.callFish();
                    ingredientsP += foodMediator.callFishP();
                } else if (FoodCategory.isSelected(jCheckBox6.getModel())) {
                    Extraingredients += foodMediator.callChicken();
                    ingredientsP += foodMediator.callChickenP();
                } else {
                    Extraingredients += "";
                    ingredientsP += "";
                }
                if (jComboBox5.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callCreamyCheese();
                    ingredientsP += foodMediator.callCreamyCheeseP();
                }
                if (jComboBox6.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenCurry();
                    ingredientsP += foodMediator.callChickenCurryP();
                }
                if (jComboBox7.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishCurry();
                    ingredientsP += foodMediator.callFishCurryP();
                }
                if (jComboBox8.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callOmletEgg();
                    ingredientsP += foodMediator.callOmletEggP();
                }
                if (jComboBox9.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenDevel();
                    ingredientsP += foodMediator.callChickenDevelP();
                }
                if (jComboBox10.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callMushroomDevel();
                    ingredientsP += foodMediator.callMushroomDevelP();
                }
                if (jComboBox11.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishPuffs();
                    ingredientsP += foodMediator.callFishPuffsP();
                }
                if (jComboBox12.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChopSuey();
                    ingredientsP += foodMediator.callChopSueyP();
                }
                if (jComboBox13.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChilliPaste();
                    ingredientsP += foodMediator.callChilliPasteP();
                }
                break;
            case 2:
                ingredientsP += foodMediator.callNasiGorengP();
                if (!jComboBox2.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();
                } else if (!jComboBox3.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();

                } else if (!jComboBox4.getSelectedItem().toString().equals("Normal")) {
                    AllIngredients += foodMediator.callIngredients();

                } else {
                    Ingredient ingredientNew = Ingredient.createInstance(jComboBox2.getSelectedItem().toString(), jComboBox3.getSelectedItem().toString(), jComboBox4.getSelectedItem().toString());
                    if (ingredientNormal == ingredientNew) {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are added", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Default levels of ingredients are Changed", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                    AllIngredients += "";
                }
                if (jComboBox5.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callCreamyCheese();
                    ingredientsP += foodMediator.callCreamyCheeseP();
                }
                if (jComboBox6.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenCurry();
                    ingredientsP += foodMediator.callChickenCurryP();
                }
                if (jComboBox7.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishCurry();
                    ingredientsP += foodMediator.callFishCurryP();
                }
                if (jComboBox8.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callOmletEgg();
                    ingredientsP += foodMediator.callOmletEggP();
                }
                if (jComboBox9.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenDevel();
                    ingredientsP += foodMediator.callChickenDevelP();
                }
                if (jComboBox10.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callMushroomDevel();
                    ingredientsP += foodMediator.callMushroomDevelP();
                }
                if (jComboBox11.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishPuffs();
                    ingredientsP += foodMediator.callFishPuffsP();
                }
                if (jComboBox12.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChopSuey();
                    ingredientsP += foodMediator.callChopSueyP();
                }
                if (jComboBox13.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChilliPaste();
                    ingredientsP += foodMediator.callChilliPasteP();
                }
                break;
            case 3:
                ingredientsP += foodMediator.callParataPotionP();
                if (jComboBox5.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callCreamyCheese();
                    ingredientsP += foodMediator.callCreamyCheeseP();
                }
                if (jComboBox6.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenCurry();
                    ingredientsP += foodMediator.callChickenCurryP();
                }
                if (jComboBox7.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishCurry();
                    ingredientsP += foodMediator.callFishCurryP();
                }
                if (jComboBox8.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callOmletEgg();
                    ingredientsP += foodMediator.callOmletEggP();
                }
                if (jComboBox9.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenDevel();
                    ingredientsP += foodMediator.callChickenDevelP();
                }
                if (jComboBox10.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callMushroomDevel();
                    ingredientsP += foodMediator.callMushroomDevelP();
                }
                if (jComboBox11.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishPuffs();
                    ingredientsP += foodMediator.callFishPuffsP();
                }
                if (jComboBox12.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChopSuey();
                    ingredientsP += foodMediator.callChopSueyP();
                }
                if (jComboBox13.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChilliPaste();
                    ingredientsP += foodMediator.callChilliPasteP();
                }
                break;
            case 4:
                ingredientsP += foodMediator.callRiceAndCurryP();
                if (FoodCategory.isSelected(jCheckBox3.getModel())) {
                    Extraingredients += foodMediator.callVeg();
                    ingredientsP += foodMediator.callVegP();
                } else if (FoodCategory.isSelected(jCheckBox4.getModel())) {
                    Extraingredients += foodMediator.callEgg();
                    ingredientsP += foodMediator.callEggP();
                } else if (FoodCategory.isSelected(jCheckBox5.getModel())) {
                    Extraingredients += foodMediator.callFish();
                    ingredientsP += foodMediator.callFishP();
                } else if (FoodCategory.isSelected(jCheckBox6.getModel())) {
                    Extraingredients += foodMediator.callChicken();
                    ingredientsP += foodMediator.callChickenP();
                } else {
                    Extraingredients += "";
                    ingredientsP += "";
                }
                if (jComboBox5.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callCreamyCheese();
                    ingredientsP += foodMediator.callCreamyCheeseP();
                }
                if (jComboBox6.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenCurry();
                    ingredientsP += foodMediator.callChickenCurryP();
                }
                if (jComboBox7.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishCurry();
                    ingredientsP += foodMediator.callFishCurryP();
                }
                if (jComboBox8.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callOmletEgg();
                    ingredientsP += foodMediator.callOmletEggP();
                }
                if (jComboBox9.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChickenDevel();
                    ingredientsP += foodMediator.callChickenDevelP();
                }
                if (jComboBox10.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callMushroomDevel();
                    ingredientsP += foodMediator.callMushroomDevelP();
                }
                if (jComboBox11.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callFishPuffs();
                    ingredientsP += foodMediator.callFishPuffsP();
                }
                if (jComboBox12.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChopSuey();
                    ingredientsP += foodMediator.callChopSueyP();
                }
                if (jComboBox13.getSelectedItem().toString().equals("No")) {
                    submenus += "";
                    ingredientsP += "";
                } else {
                    submenus += foodMediator.callChilliPaste();
                    ingredientsP += foodMediator.callChilliPasteP();
                }
                break;
            default:
                Extraingredients = "";
                break;
        }

        if (FoodCategory.isSelected(jCheckBox1.getModel())) {
            supplyType = "Take Away";
        } else if (FoodCategory.isSelected(jCheckBox2.getModel())) {
            supplyType = "Dine In";
        } else {
            supplyType = "";
        }

        builder1.setIngredient(AllIngredients + Extraingredients);
        builder1.setSubMenu(submenus);
        builder1.setFoodprice(ingredientsP);
        Order order1 = builder1.build();

        String content = order1.getFoodprice();

        char charArray[] = content.toCharArray();

        ArrayList<String> expressionList = new ArrayList<>();

        String text = "";
        for (char c : charArray) {

            if (Character.isDigit(c)) {
                text += c;
            } else {
                expressionList.add(text);
                text = "";
                expressionList.add(String.valueOf(c));
            }

        }
        expressionList.add(text);
        //Logic

        //Interpret
        Expression expression = new TerminalExpression(Integer.parseInt(expressionList.get(0)));

        for (int i = 0; i < expressionList.size(); i++) {

            if (expressionList.get(i).equals("+")) {
                Expression next = new TerminalExpression(Integer.parseInt(expressionList.get(i + 1)));
                expression = new NonTerminalExpression(expression, next);

            }

        }

        foodDescription += order1.toString() + "\n";
        foodPrice += order1.getFoodprice() + "\n";
        FinalPrice += expression.Interpret();
        jTextArea1.setText(foodDescription);
        jTextArea2.setText(foodPrice + "\n" + " = Rs." + FinalPrice + "/=");

        resetMenu();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        foodDescription = "";
        foodPrice = "";
        FinalPrice = 0;
        jTextArea1.setText("");
        jTextArea2.setText("");

        resetMenu();

    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SyzygyFoodCorner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SyzygyFoodCorner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SyzygyFoodCorner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SyzygyFoodCorner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SyzygyFoodCorner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup FoodCategory;
    public javax.swing.ButtonGroup SupplyType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    public javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
