package by.AndreyShutikov;
import java.util.Random;
import java.util.Scanner;

    public class Main {
    public static char USER_SIGN = 'X'; //Игрок
    public static  char AI_SIGN = 'O'; //ИИ
    public static  char NOT_SIGN = '*'; //Пустая клетка
    public static char[][] map;
    public static int SIZE=5;  // Размер поля
    public static int DOTS_TO_WIN = 4;    // Сколько должно быть одинаковых значение подряд для победы


    public static void main(String[] args)
    {
        int count = 0;
        initMap();
        while (true) {
            printField();
            userShot(USER_SIGN, 0);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("Пользователь человек выйграл!");
                printField();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }
            aiShot();
            count++;
            if (checkWin(AI_SIGN)) {
                System.out.println("ИИ выйграл!");
                printField();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }

        }
    }



/**Стартовое заполнение*/
static void initMap() {
    map = new char[SIZE][SIZE];
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            map[i][j] = NOT_SIGN;
        }
    }
}

/**Печать таблицы*/
    public static void printField() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**Ходы игрока*/
    public static void userShot(char symb, int i) {
        int x = -1;
        int y = -1;
        do {
            if (i == 0) {
                System.out.println("Введите координаты x y (1 - " + SIZE + "): ");
            } else {
                System.out.println("Игрок " + i + ". Введите координаты x y (1 - " + SIZE + "): ");
            }
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }
        while (isCellBusy(x, y));
        map[x][y] = symb;
    }

    /**Ходы ИИ*/
    public static void aiShot() {
        int x = -1;
        int y = -1;
        boolean ai_win = false;
        boolean user_win = false;
        // Находим выигрышный ход
                   for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!isCellBusy(i, j)) {
                        map[i][j] = AI_SIGN;
                        if (checkWin(AI_SIGN)) {
                            x = i;
                            y = j;
                            ai_win = true;
                        }
                        map[i][j] = NOT_SIGN;
                    }
                }
            }

        // Блокировка хода пользователя, если он побеждает на следующем ходу

            if (!ai_win) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        if (!isCellBusy(i, j)) {
                            map[i][j] = USER_SIGN;
                            if (checkWin(USER_SIGN)) {
                                x = i;
                                y = j;
                                user_win = true;
                            }
                            map[i][j] = NOT_SIGN;
                        }
                    }
                }
            }


        if (!ai_win && !user_win) {
            do {
                Random rnd = new Random();
                x = rnd.nextInt(SIZE);
                y = rnd.nextInt(SIZE);
            }
            while (isCellBusy(x, y));
        }
        map[x][y] = AI_SIGN;
        System.out.println(" ИИ походил: x = " + x + "| y = " + y);
    }

/** Заполнена ли таблица*/
    public static boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > SIZE - 1 || y > SIZE - 1) {
            return false;
        }
        return map[x][y] != NOT_SIGN;
    }


 /**Проверка выйгрыша*/
    static boolean checkWin(char symb) {
        for (int col=0; col<SIZE-DOTS_TO_WIN+1; col++) {
            for (int row=0; row<SIZE-DOTS_TO_WIN+1; row++) {
                if (checkDiagonal(symb, col, row) || checkLanes(symb, col, row)) return true;
            }
        }
        return false;
    }

    /** Проверяем диагонали */
    static boolean checkDiagonal(char symb, int offsetX, int offsetY) {
        boolean toright, toleft;
        toright = true;
        toleft = true;
        for (int i=0; i<DOTS_TO_WIN; i++) {
            toright &= (map[i+offsetX][i+offsetY] == symb);
            toleft &= (map[DOTS_TO_WIN-i-1+offsetX][i+offsetY] == symb);
        }

        if (toright || toleft) return true;

        return false;
    }

    /**Заполненность таблицы*/
   static boolean isMapFull()
   {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == NOT_SIGN) return false;
            }
        }
        return true;
    }

    /** Проверяем горизонтальные и вертикальные линии */
    static boolean checkLanes(char symb, int offsetX, int offsetY) {
        boolean cols, rows;
        for (int col=offsetX; col<DOTS_TO_WIN+offsetX; col++) {
            cols = true;
            rows = true;
            for (int row=offsetY; row<DOTS_TO_WIN+offsetY; row++) {
                cols &= (map[col][row] == symb);
                rows &= (map[row][col] == symb);
            }

            if (cols || rows) return true;
        }

        return false;
    }


}