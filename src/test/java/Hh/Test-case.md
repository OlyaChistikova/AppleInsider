## Test-case:
1. Открыть резюме, которое доступно только по ссылке: https://hh.ru/applicant/resumes/view?resume=1edf0c93ff095811d20039ed1f6a3638497073
2. Получить информацию о профиле, используя HashMap или Class, в которой должны быть следующие атрибуты: String sex, int age, String city, boolean confirmedPhoneNumber, boolean readyToRelocate.
3. Убедится, что ожидаемый результат и актуальный равны.

   Ожидаемый результат sex - "М", age = 25, city - "Санкт-Петербург", confirmedPhoneNumber - true, readyToRelocate - false;