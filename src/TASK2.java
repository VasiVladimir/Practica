public class TASK2 {
    public static void main(String[] args) {
        // Создание массива объектов пациентов
        Patient[] patients = new Patient[]{
                new Patient(1, "Ivanov", "Ivan", "Ivanovich", "Moscow, Lenin str. 1", "111-111-111", 12345, "Flu"),
                new Patient(2, "Petrov", "Petr", "Petrovich", "Moscow, Red square 5", "222-222-222", 54321, "Headache"),
                new Patient(3, "Sidorov", "Sidor", "Sidorovich", "Saint-Petersburg, Nevsky str. 10", "333-333-333", 67890, "Diabetes"),
                new Patient(4, "Kuznetsov", "Vladimir", "Vladimirovich", "Novosibirsk, Central st. 20", "444-444-444", 13579, "Flu"),
                new Patient(5, "Smirnov", "Sergey", "Sergeevich", "Ekaterinburg, Victory st. 7", "555-555-555", 98765, "Headache")
        };

        String searchDiagnosis = "Flu";
        int cardNumberLower = 10000;
        int cardNumberUpper = 60000;

        // Вывод списка пациентов с заданным диагнозом
        System.out.println("Пациенты с диагнозом " + searchDiagnosis + ":");
        for (Patient patient : patients) {
            if (patient.getDiagnosis().equals(searchDiagnosis)) {
                System.out.println(patient);
            }
        }

        // Вывод списка пациентов, у которых номер медицинской карты находится в заданном интервале
        System.out.println("\nПациенты с номером медицинской карты в интервале от " + cardNumberLower + " до " + cardNumberUpper + ":");
        for (Patient patient : patients) {
            if (patient.getMedicalCardNumber() >= cardNumberLower && patient.getMedicalCardNumber() <= cardNumberUpper) {
                System.out.println(patient);
            }
        }
    }
}