package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.repository.AccountRepository;

import java.io.*;
import java.util.*;

public class FileAccountRepository implements AccountRepository {
    /** Значение пути до файла */
    private final String filePath;

    /** Строковый список, хранящий полную копию данных
     * из файла (для сохранения формата записи и избежания
     * частого чтения при перезаписи)*/
    private List<String> rawData;

    /** Список индексов строк с клиентскими данными для
     * быстрого перемещения по исходным данным из файла */
    private List<Integer> accountStrIndices;

    /** Map для хранения пар идентификаторов клиентов
     * и соответсвующего им спсика аккаунтов */
    private Map<Long, List<Long>> clientDataMap;

    /**
     * Создает объект @{code FileAccountRepository} и
     * производит выделение клиентсикх данных из
     * считанного файла, расположенного по пути {@code filePath}
     *
     * @param filePath
     *        Путь до рабочего файлв
     */
    public FileAccountRepository(String filePath) {
        this.filePath = filePath;
        readDataFromFile();
        getStrIndicesOfAccounts();
        parseRawData();
    }

    /**
     * Заполняет {@code rawData} всеми данными из файла по
     * {@code filePath}, считываемого построчно.
     *
     * @throws RuntimeException
     *         Если по заданному {@code filePath} не найден
     *         файл на момент исполнения
     */
    private void readDataFromFile() {
        rawData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String s;
            while ((s = br.readLine()) != null) {
                rawData.add(s + '\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Задан некорректный путь к файлу", e);
        }
    }

    /**
     * Перезаписывает файл по {@code filePath} всеми строками,
     * имеющимися в {@code rawData}.
     *
     * @throws RuntimeException
     *         Если по заданному {@code filePath} не найден
     *         файл на момент исполнения
     */
    private void writeDataToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String s : rawData) {
                bw.write(s);
            }
        } catch (IOException e) {
            throw new RuntimeException("Задан некорректный путь к файлу", e);
        }
    }

    /**
     * Заполняет {@code accountStrIndices} индексами строк из {@code rawData} для
     * последующего быстрого перемещения по {@code rawData} при поиске и выделении
     * отделных клиентских данных. На основе регулярного выражения проверяется содержат
     * ли следующие друг за другом строки информацию о клиентском аккаунте.
     */
    private void getStrIndicesOfAccounts() {
        final String noLetterRegex = "[^a-zA-Z]";
        final String clientId = "clientId";
        final String number = "number";
        accountStrIndices = new ArrayList<>();

        for (int i = 0; i < rawData.size() - 1; i++) {
            if (rawData.get(i).replaceAll(noLetterRegex, "").equals(clientId) &&
                    rawData.get(i + 1).replaceAll(noLetterRegex, "").equals(number)) {
                accountStrIndices.add(i);
            }
        }
    }

    /**
     * Заполняет {@code clientDataMap} парами {@code clientId} и списков связанных аккаунтов.
     * Выделение числовых значений проивзодится на основе известных индексов строк из
     * {@code accountStrIndices}, а также применения регулярных выражений.
     */
    private void parseRawData() {
        clientDataMap = new HashMap<>();
        final String noDigitRegex = "\\D";

        for (int keyIndex : accountStrIndices) {
            long key = Long.parseLong(rawData.get(keyIndex).replaceAll(noDigitRegex, ""));
            if (!clientDataMap.containsKey(key)) {
                clientDataMap.put(key, new ArrayList<>());
            }
            // valueIndex == keyIndex + 1
            long value = Long.parseLong(rawData.get(keyIndex + 1).replaceAll(noDigitRegex, ""));
            clientDataMap.get(key).add(value);
        }
    }

    /**
     * Производит проверку на существование в {@code clientDataMap} задаваемого {@code clientId}
     * и наличие для него заменяемого {@code oldAccount}. В случае успеха заменяет {@code oldAccount}
     * на {@code newAccount} в {@code clientDataMap} и {@code rawData} с последующей записью в файл.
     *
     * @param clientId
     *        Идентификатор клиента
     *
     * @param oldAccount
     *        Номер заменяемого аккаунта
     *
     * @param newAccount
     *        Номер замещающего аккаунта
     *
     * @throws RuntimeException
     *         Если {@code clientDataMap}, включающий данные о клиентских
     *         аккаунтах, не содержит запрашиваемого {@code clientId}
     *
     * @throws RuntimeException
     *         Если для заданного {@code clientId} не существует аккаунта
     *         со значением {@code oldAccount}
     */
    @Override
    public void updateAccountById(long clientId, long oldAccount, long newAccount) {
        //проверка что clientId существует
        //проверка что у clientId существует account
        //замена oldAccount на newAccount в clearData
        //замена oldAccount на newAccount в rawData
        //запись rawData в файл
        if (!clientDataMap.containsKey(clientId)) {
            throw new RuntimeException("Несуществующий clientId");
        }
        int indexOldId = clientDataMap.get(clientId).indexOf(oldAccount);
        if (indexOldId == -1) {
            throw new RuntimeException("Несуществующий account для заданного clientId");
        }
        clientDataMap.get(clientId).set(indexOldId, newAccount);

        final String noDigitRegex = "\\D";
        for (int keyIndex : accountStrIndices) {
            int valueIndex = keyIndex + 1;
            if (Long.parseLong(rawData.get(keyIndex).replaceAll(noDigitRegex, "")) == clientId &&
                    Long.parseLong(rawData.get(valueIndex).replaceAll(noDigitRegex, "")) == oldAccount) {
                String updatedAccount = rawData.get(valueIndex)
                        .replace(Long.toString(oldAccount), Long.toString(newAccount));
                rawData.set(valueIndex, updatedAccount);
            }
        }
        writeDataToFile();
    }

    /**
     * Возвращает {@code Set<Long>} всех аккаунтов, имеющихся для
     * запрашиваемого {@code clientId}.
     *
     * @param clientId
     *        Идентификатор клиента
     *
     * @throws RuntimeException
     *         Если {@code clientDataMap}, включающий данные о клиентских
     *         аккаунтах, не содержит запрашиваемого {@code clientId}
     */
    @Override
    public Set<Long> getAllAccountsByClientId(long clientId) {
        if (!clientDataMap.containsKey(clientId)) {
            throw new RuntimeException("Несуществующий clientId");
        }
        return new HashSet<>(clientDataMap.get(clientId));
    }

    @Override
    public String getUserAgreementDateByClientId(long clientId) {
        //без реализации (не требуется)
        return null;
    }
}
