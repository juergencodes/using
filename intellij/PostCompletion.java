public class PostCompletion {

    public void post(final Object x, final String[] a) {
        // x.nn
        if (x != null) {

        }

        // x.instanceof
        final String s = (x instanceof String) ? ((String) x) : null;

        // s.format
        String.format(s, "x");

        // x.castvar
        Boolean b = (Boolean) x;

        // b.if
        if (b) {

        }

        // b.else
        if (!b) {

        }

        // a.for
        for (String s1 : a) {

        }

        // a.fori
        for (int i = 0; i < a.length; i++) {

        }

        // a.stream
        Arrays.stream(a);


        // m().lambda
        () -> m();

        // m().try
        try {
            m();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // new ByteArrayInputStream.twr
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream()) {

        } catch (IOException exception) {
        }
    }

    private void m() throws Exception {

    }
}