# mifmi-commons4j

[![](https://jitpack.io/v/mifmi/mifmi-commons4j.svg)](https://jitpack.io/#mifmi/mifmi-commons4j)

The mifmi-commons4j presents basic utility classes for Java development. 

## Getting started

## How to use

### NamedFormatter - org.mifmi.commons4j.text.format

    // Define value with name
    Map<String, Object> vars = new HashMap<>();
    vars.put("varNull", null);
    vars.put("varStr", "val");
    vars.put("varInt", 123456);
    vars.put("varDec", new BigDecimal("123456.789"));
    vars.put("varDate", OffsetDateTime.parse("2000-01-23T04:05:06.123456789+09:00"));
    vars.put("varBool", true);
    
    
    //// Refer value as '${varName}'
    
    // Refer value
    Assert.assertEquals("val", new NamedFormatter("${varStr}").format(vars));
    Assert.assertEquals("FOOvalBAR123456BAZ", new NamedFormatter("FOO${varStr}BAR${varInt}BAZ").format(vars));
    
    
    //// Format value as '${varName%format}'
    
    // Format Integer value
    Assert.assertEquals("123,456", new NamedFormatter("${varInt%#,##0}").format(vars));
    Assert.assertEquals("123,456.000000", new NamedFormatter("${varInt%#,##0.000000}").format(vars));
    
    // Format Decimal value
    Assert.assertEquals("123,457", new NamedFormatter("${varDec%#,##0}").format(vars));
    Assert.assertEquals("123,456.789000", new NamedFormatter("${varDec%#,##0.000000}").format(vars));
    
    // Format Date value
    Assert.assertEquals("20000123 040506123", new NamedFormatter("${varDate%uuuuMMdd HHmmssSSS}").format(vars));
    
    
    //// Choice value as '${varName/val1:ret1/val2:ret2/default:retDefault}'
    //// Operator:
    ////   != is Not
    ////   < is Less than
    ////   <= is Less than equals
    ////   > is Greater than
    ////   >= is Greater than equals
    
    // Choice String value
    Assert.assertEquals("Z", new NamedFormatter("${varStr/xxx:X/yyy:Y/default:Z}").format(vars));
    Assert.assertEquals("Y", new NamedFormatter("${varStr/xxx:X/val:Y/default:Z}").format(vars));
    Assert.assertEquals("Z", new NamedFormatter("${varStr/xxx:X/!=val:Y/default:Z}").format(vars));
    
    // Choice Integer value
    Assert.assertEquals("Y", new NamedFormatter("${varInt/<123456:X/123456:Y/>123456:Z}").format(vars));
    
    // Choice Decimal value
    Assert.assertEquals("Y", new NamedFormatter("${varDec/<123456.789:X/123456.789:Y/>123456.789:Z}").format(vars));
    
    // Choice Date value
    Assert.assertEquals("Y", new NamedFormatter("${varDate/<#2000-01-23#:X/#2000-01-23#:Y/>#2000-01-23#:Z}").format(vars));
    Assert.assertEquals("Y", new NamedFormatter("${varDate/<#2000-01-23T04:05:06.123456789+09:00#:X/#2000-01-23T04:05:06.123456789+09:00#:Y/>#2000-01-23T04:05:06.123456789+09:00#:Z}").format(vars));
    
    // Choice Boolean value
    Assert.assertEquals("Y", new NamedFormatter("${varBool/false:X/true:Y}").format(vars));
    Assert.assertEquals("X", new NamedFormatter("${varBool/!=false:X/!=true:Y}").format(vars));
    
    // Choice Null value
    Assert.assertEquals("Y", new NamedFormatter("${varNull/'null':X/null:Y/default:Z}").format(vars));

### Config - org.mifmi.commons4j.config

    Config config = new ResourceBundleConfig(
            "testconfig",
            new Locale("ja", "JP"),
            new Locale("en", "US")
            );
    
    String stringVal = config.getAsString("test.string");
    int intVal = config.getAsInt("test.int");
    boolean booleanVal = config.getAsBool("test.boolean");
    String[] arrayVal = config.getAsArray("test.array");

### Datastore - org.mifmi.commons4j.datastore

Load from datasource.

    ConnectionManager connectionManager = ConnectionManager.getInstance();
    Connection defaultConnection = connectionManager.getConnection();
    Connection db1Connection = connectionManager.getConnection("db1");

Load from config.

    // <database.properties>
    // datastore.default.driver=com.mysql.jdbc.Driver
    // datastore.default.url=jdbc:mysql://example/example
    // datastore.default.user=dbuser
    // datastore.default.password=dbpassword
    // 
    // datastore.db1.driver=com.mysql.jdbc.Driver
    // datastore.db1.url=jdbc:mysql://example/example
    // datastore.db1.user=dbuser
    // datastore.db1.password=dbpassword
    
    Config config = new ResourceBundleConfig("database");
    ConnectionManager.setDefaultConnectionLoader(
            new ConfigConnectionLoader(config, "datastore.")
            );
    
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    Connection defaultConnection = connectionManager.getConnection();
    Connection db1Connection = connectionManager.getConnection("db1");

### Crypto - org.mifmi.commons4j.crypto

    Cryptor cryptor = new DESedeCryptor(createKey(192), "ECB", "PKCS5Padding");
    
    byte[] val = new byte[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
    
    // DESede with Binary
    byte[] encValBin = cryptor.encrypt(val);
    byte[] decValBin = cryptor.decrypt(encValBin);
    
    // DESede with InputStream
    InputStream encValIS = cryptor.encrypt(new ByteArrayInputStream(val));
    InputStream decValIS = cryptor.decrypt(encValIS);
    
    // DESede with OutputStream
    ByteArrayOutputStream encValOS = new ByteArrayOutputStream(1024);
    try (OutputStream encTemp = cryptor.encrypt(encValOS)) {
        encTemp.write(val);
    }
    ByteArrayOutputStream decValOS = new ByteArrayOutputStream(1024);
    try (OutputStream decTemp = cryptor.decrypt(decValOS)) {
        decTemp.write(encValOS.toByteArray());
    }

### Value Filter (Validator &amp; Converter) - org.mifmi.commons4j.valuefilter

String filter.

    Object value = "Foo Bar\0Baz";
        String ret = new ValueFilters()
            .add(new RequiredValidator())
            .add(new StrLengthValidator(1, 20))
            .add(new StrNullCharConverter(" "))
            .add(new StrUpperCaseConverter())
            .add(new StrReplaceCharConverter('B', "V"))
            .filter(value);
    assertEquals("FOO VAR VAZ", ret);

String to Long filter.

    Object value = "1,234";
    Long ret = new ValueFilters()
            .add(new RequiredValidator())
            .add(new StrToLongConverter("#,##0"))
            .add(new NumberRangeValidator(1, 1234))
            .filter(value);
    assertEquals(Long.valueOf(1234L), ret);

Array filter.

    Object value = new Object[]{"123", "456", "789"};
    Object[] ret = new ValueFilters()
            .add(new RequiredValidator())
            .add(new EachElementFilter(new RequiredValidator()))
            .add(new EachElementFilter(new StrLengthValidator(1, 3)))
            .add(new EachElementFilter(new StrToIntegerConverter("#,##0")))
            .filter(value);
    assertArrayEquals(new Object[]{123, 456, 789}, ret);

Invalid scara value result.

    ValueFilter<?, ?> filterForAssert = null;
    try {
        Object value = "1,234";
        Long ret = new ValueFilters()
                .add(new RequiredValidator())
                .add(new StrToLongConverter("#,##0"))
                .add(filterForAssert = new NumberRangeValidator(1, 1233))
                .filter(value);
        fail();
    } catch (InvalidValueException e) {
        assertEquals(Long.valueOf(1234L), e.getValue());
        assertEquals(null, e.getValueKey());
        assertEquals(filterForAssert, e.getValueFilter());
        assertEquals(NumberRangeValidator.VR_TOO_BIG, e.getValidationResult());
    }

Invalid array value result.

    ValueFilter<?, ?> filterForAssert = null;
    try {
        Object value = new Object[]{"123", "4567", "789"};
        Object[] ret = new ValueFilters()
                .add(new RequiredValidator())
                .add(new EachElementFilter(new RequiredValidator()))
                .add(new EachElementFilter(filterForAssert = new StrLengthValidator(1, 3)))
                .add(new EachElementFilter(new StrToIntegerConverter("#,##0")))
                .filter(value);
        fail();
    } catch (InvalidValueException e) {
        assertEquals("4567", e.getValue());
        assertEquals(Integer.valueOf(1), e.getValueKey());
        assertEquals(filterForAssert, e.getValueFilter());
        assertEquals(StrLengthValidator.VR_TOO_LONG, e.getValidationResult());
    }

## License

The mifmi-commons4j is open-sourced software licensed under the [MIT license](https://github.com/mifmi/mifmi-commons4j/blob/master/LICENSE).
