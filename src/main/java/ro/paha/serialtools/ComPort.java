package ro.paha.serialtools;

import com.fazecast.jSerialComm.SerialPort;
import ro.paha.serialtools.repository.Repository;

public class ComPort {

    private String Id;
    private String name;
    private String nickName = "";
    private String Description;
    private int BaudRate;
    private int DataBits;
    private int StopBits;
    private int Parity;
    private SerialPort comPort;
    private boolean isConnected;
    private Repository repository;

    public ComPort(String Id, String Name, String Description) {
        this.Id = Id;
        this.name = Name;
        this.Description = Description;
        this.isConnected = false;
    }

    public SerialPort getComPort() {
        return comPort;
    }

    public void setComPort(SerialPort comPort) {
        this.comPort = comPort;
        this.isConnected = true;
    }

    public void removeComPort() {
        this.comPort = null;
        this.isConnected = false;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String name) {
        nickName = name;
    }

    public String getDescription() {
        return Description;
    }

    public int getBaudRate() {
        return BaudRate;
    }

    public void setBaudRate(int baudRate) {
        BaudRate = baudRate;
    }

    public int getDataBits() {
        return DataBits;
    }

    public void setDataBits(int dataBits) {
        DataBits = dataBits;
    }

    public int getStopBits() {
        return StopBits;
    }

    public void setStopBits(int stopBits) {
        StopBits = stopBits;
    }

    public int getParity() {
        return Parity;
    }

    public void setParity(int parity) {
        Parity = parity;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String toString() {
        return this.getName();
    }

    public static enum BAUDRATE {

        B0(0, "0"),
        B50(50, "50"),
        B75(75, "75"),
        B110(110, "110"),
        B134(134, "134"),
        B150(150, "150"),
        B200(200, "200"),
        B300(300, "300"),
        B600(600, "600"),
        B1200(1200, "1200"),
        B1800(1800, "1800"),
        B2400(2400, "2400"),
        B4800(4800, "4800"),
        B9600(9600, "9600"),
        B14400(14400, "14400"),
        B19200(19200, "19200"),
        B28800(28800, "28800"),
        B38400(38400, "38400"),
        B56000('\udac0', "56000"),
        B57600('\ue100', "57600"),
        B115200(115200, "115200"),
        B128000(128000, "128000"),
        B153600(153600, "153600"),
        B230400(230400, "230400"),
        B256000(256000, "256000"),
        B460800(460800, "460800"),
        B500000(500000, "500000"),
        B576000(576000, "576000"),
        B921600(921600, "921600"),
        B1000000(1000000, "1000000"),
        B1152000(1152000, "1152000"),
        B1500000(1500000, "1500000"),
        B2000000(2000000, "2000000"),
        B2500000(2500000, "2500000"),
        B3000000(3000000, "3000000"),
        B3500000(3500000, "3500000"),
        B4000000(4000000, "4000000"),
        BCUSTOM(251, "custom");

        private int value;
        private String name;

        private BAUDRATE(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return name;
        }
    }

    public static enum PARITY {

        P_NONE(0, "None"),
        P_ODD(1, "Odd"),
        P_EVEN(2, "Even"),
        P_MARK(3, "Mark"),
        P_SPACE(4, "Space");

        private int value;
        private String name;

        private PARITY(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return name;
        }
    }

    public static enum STOPBITS {

        SB_1(1, "1"),
        SB_1_5(4, "1.5"),
        SB_2(2, "2");

        private int value;
        private String name;

        private STOPBITS(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return name;
        }
    }

    public static enum DATABITS {

        DB_5(5),
        DB_6(6),
        DB_7(7),
        DB_8(8);

        private int value;
        private String name;

        private DATABITS(int value) {
            this.value = value;
            this.name = Integer.toString(value);
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return name;
        }
    }

    public static enum FLOWCONTROL {

        NONE(1, "None"),
        RTS_CTS(2, "RTS/CTS"),
        DTR_DSR(3, "Dtr/Dsr"),
        XON_XOFF(4, "Xon/Xoff");

        private int value;
        private String name;

        private FLOWCONTROL(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return name;
        }
    }
}
