
import java.io.*;
import java.time.LocalDate;
import java.util.*;


class hotelBookingService {

    //instances required

    private  final  String  fileName = "HotelBookings.txt";
    private List<Rooms> rooms;
    private List<Bookings> booking;

    Scanner input = new Scanner(System.in);

// auto trigger constructor

    public hotelBookingService(){
        this.rooms = new ArrayList<>();
        this.booking = new ArrayList<>();
        getDATA();
        if(rooms.isEmpty()) roomsInHotel();
    }


// Method to select run all methods


    private void mainRunMethod (){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View details ");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if(choice.equalsIgnoreCase("1")){
                searchRoom();
            } else if (choice.equalsIgnoreCase("2")) {
                bookRoom();
            } else if (choice.equalsIgnoreCase("3")) {
                cancelRoom();
            } else if (choice.equalsIgnoreCase("4")) {
                customerDetailsView();
            }else if  (choice.equalsIgnoreCase("0")){
                SaveData();
            }
        }
    }

// Room class Objects are  Added to the List<> rooms ...

    private void roomsInHotel(){
        rooms.add(new Rooms(101,"Standard","Ac",2000));
        rooms.add(new Rooms(201,"Deluxe","Ac",3000));
        rooms.add(new Rooms(202,"Deluxe","Ac",3000));
        rooms.add(new Rooms(301,"Suite","Ac",4500));

    }



// searching room

    private void searchRoom(){
        System.out.println(" Enter the room richness type >>>");
        System.out.print("Standard / deluxe / suite  : ");
        String roomType = input.next();
        boolean roomFound = false;
        for (int i = 0; i < rooms.size(); i++) {
            Rooms r = rooms.get(i);
            if(r.getRoomType().equalsIgnoreCase(roomType) && RoomAvailable(r.getRoomNo())){
                System.out.println(r);
                roomFound = true;

            }

        }
        if(!roomFound){
            System.out.println("sorry! Rooms  are not available at this time ");
        }

    }



// room booking

    private void bookRoom(){
        System.out.print("Enter the name  : ");
        String guestName = input.next();
        System.out.println(" Enter the room richness type >>>");
        System.out.print("Standard / deluxe / suite  : ");
        String roomType = input.next();
        Rooms available = null;
        int i = 0;
        while(i < rooms.size()){
            Rooms r = rooms.get(i);
            if(r.getRoomType().equalsIgnoreCase(roomType) && RoomAvailable(r.getRoomNo())){
                available = r;
                break;
            }
            i++;
        }
        if(available == null){
            System.out.println("sorry! Rooms are  not available such type !!");
            return;
        }

        System.out.print("Enter the checkIN Date YYYY-MM-DD  : ");
        LocalDate in = LocalDate.parse(input.next());
        System.out.print("Enter the checkOUT Date YYYY-MM-DD  : ");
        LocalDate out = LocalDate.parse(input.next());
        long days = in.until(out).getDays();
        double totalAmount = days * available.getCostOfRooms();

        System.out.println("Total amount = " + totalAmount);
        if(!payment(totalAmount)){
            System.out.println("Payment failed  !!");
        }

        Bookings b = new Bookings(UUID.randomUUID().toString(),guestName,available.getRoomNo(),in,out);
        booking.add(b);
        System.out.println( "greetings Your booking has completed your ID is : " + b.getId());

    }



//room cancellation

    private void cancelRoom(){
        System.out.print("Enter the Booking id  : ");
        String id = input.next();
        Bookings b = null;
        for (Bookings bk : booking) {
            if (bk.getId().equals(id)) { b = bk; break; }
        }
        if (b != null) {
            booking.remove(b);
            System.out.println("Booking cancelled.");
        } else {
            System.out.println("Booking not found.");
        }


    }



// customer Details

    private void customerDetailsView(){
        System.out.print("Enter the name of the customer : ");
        String guestName = input.next();
        int i = 0;
        while(i < booking.size()){
            Bookings b = booking.get(i);
            if(!(guestName.equalsIgnoreCase(b.getCustomerName()))){
                System.out.println("No bookings available ..");
            }else {
                System.out.println(b);
            }
            i++;

        }

    }


// check about room is vacant or else

    private boolean RoomAvailable(int roomNumber){
        int i = 0;
        while(i < booking.size()){
            Bookings b = booking.get(i);
            if(b.getRoomNumber() == roomNumber){
                return false;
            }
            i++;
        }
        return true;

    }

// Payment method

    private boolean payment(double amount){
        System.out.println("Payment process amount of :  " + amount);
        return new Random().nextDouble() > 0.1;
    }

// FileInput  --> read the  data in the file

    @SuppressWarnings("unchecked")
    private void getDATA(){
        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rooms = (List<Rooms>) ois.readObject();
            booking = (List<Bookings>) ois.readObject();
        }catch (Throwable th){
            rooms = new ArrayList<>();
            booking = new ArrayList<>();
        }
    }

//FileOutput --->  save the user entered data

    private void SaveData(){
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ois.writeObject(rooms);
            ois.writeObject(booking);
        }catch(Throwable th){
            System.out.println("issue while Saving the data ");
        }
    }



// Bookings class --> custom class --> saves the bookings in the form of objects

    public static class Bookings implements Serializable {
        private String Id;
        private String customerName;
        private int roomNumber;
        private LocalDate checkIn;
        private  LocalDate checkOut;

        public Bookings(String Id ,String customerName ,int roomNumber ,LocalDate checkIn,LocalDate checkOut){
            this.Id = Id;
            this.customerName = customerName;
            this.roomNumber = roomNumber;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }
        public String getId(){
            return Id;

        }
        public String getCustomerName(){
            return customerName;
        }
        public  int getRoomNumber(){
            return roomNumber;
        }

        public LocalDate getCheckIn() {
            return checkIn;
        }

        public LocalDate getCheckOut() {
            return checkOut;
        }

        @Override
        public String toString() {
            return "Booking details :  Customer Id : " + Id +" Customer name : " +
                    customerName + " Room number : " + roomNumber;
        }
    }



    // Rooms class --> which store the hotel rooms in the form of objects

    public static class Rooms implements Serializable {

        private int RoomNo;
        private String RoomType;
        private String AcNONAc;
        private double CostOfRooms;

        public Rooms(int RoomNo ,String RoomType,String AcNONAc ,double CostOfRooms){
            this.RoomNo = RoomNo;
            this.RoomType = RoomType;
            this.AcNONAc = AcNONAc;
            this.CostOfRooms = CostOfRooms;

        }
        public int getRoomNo(){
            return RoomNo;
        }
        public String getRoomType(){
            return RoomType;

        }public  String getAcNONAc(){
            return AcNONAc;
        }
        public double getCostOfRooms(){
            return CostOfRooms;
        }

        @Override
        public String toString() {
            return  "Room details  >>  :: Room Number  : " + RoomNo +" , Room type : " +
                    RoomType + " , Cost Of rooms : " + CostOfRooms;
        }
    }






    // execution gate

    public static void main(String [] args){
        System.out.println("=== Welcome to roshans Hotel ===");
        hotelBookingService hotelBookings = new hotelBookingService();
        hotelBookings.mainRunMethod();
    }

}

