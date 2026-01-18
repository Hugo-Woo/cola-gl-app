@echo off
echo Adding Voucher...
curl -H "Content-Type: application/json" -X POST -d "{\"voucherDTO\":{\"voucherCode\":\"V001\",\"accountingDate\":\"2024-01-18\",\"description\":\"Test Voucher\",\"lines\":[{\"accountCode\":\"1001\",\"direction\":\"D\",\"amount\":100.00,\"description\":\"Debit\"},{\"accountCode\":\"2001\",\"direction\":\"C\",\"amount\":100.00,\"description\":\"Credit\"}]}}" http://localhost:8080/api/voucher
echo.

echo Posting Voucher (ID assumed 1)...
curl -H "Content-Type: application/json" -X POST -d "{\"voucherId\":1}" http://localhost:8080/api/voucher/post
echo.

echo Checking Balance (Account 1001)...
curl "http://localhost:8080/api/balance?accountCode=1001&period=2024-01"
echo.

echo Checking Balance (Account 2001)...
curl "http://localhost:8080/api/balance?accountCode=2001&period=2024-01"
echo Submitting Voucher (Inter-company)...
curl -H "Content-Type: application/json" -X POST -d "{\"voucherDTO\":{\"voucherCode\":\"V002\",\"accountingDate\":\"2024-01-18\",\"description\":\"Sub-Ledger Voucher\",\"lines\":[{\"accountCode\":\"3001\",\"direction\":\"D\",\"amount\":500.00,\"description\":\"Inter-company Debit\",\"contactCode\":\"C001\"},{\"accountCode\":\"2001\",\"direction\":\"C\",\"amount\":500.00,\"description\":\"Credit\"}]}}" http://localhost:8080/api/voucher
echo.
echo Submit operation (ID 2 assumed)...
curl -H "Content-Type: application/json" -X POST -d "{\"voucherId\":2}" http://localhost:8080/api/voucher/submit
echo.
echo Checking Sub-Ledger Balance (Account 3001, Contact C001)...
curl "http://localhost:8080/api/balance?accountCode=3001&contactCode=C001&period=2024-01"
echo.
echo Done.
