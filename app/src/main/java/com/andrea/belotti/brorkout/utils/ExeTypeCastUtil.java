package com.andrea.belotti.brorkout.utils;

public class ExeTypeCastUtil {

    /*public void changeTypeExeFragment(Spinner typeNumPicker) {
        typeNumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = typeNumPicker.getSelectedItem().toString();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                DataExeSerFragment esercizioSerieFragment = new DataExeSerFragment();
                DataExePirFragment esercizioPiramidaleFragment = new DataExePirFragment();
                DataExeIncrFragment esercizioIncrementaleFragment = new DataExeIncrFragment();
                DataExeTenFragment esercizioTenuteFragment = new DataExeTenFragment();

                switch (selectedItem) {
                    case "Serie":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, esercizioSerieFragment);
                        typeExeFragment = esercizioSerieFragment;
                        break;
                    case "Incrementale":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, esercizioIncrementaleFragment);
                        typeExeFragment = esercizioIncrementaleFragment;
                        break;
                    case "Piramidale":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, esercizioPiramidaleFragment);
                        typeExeFragment = esercizioPiramidaleFragment;
                        break;
                    case "Tenuta":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, esercizioTenuteFragment);
                        typeExeFragment = esercizioTenuteFragment;
                        break;
                    default:
                        Toast toast = Toast.makeText(context, "TIPO NON VALIDO", duration);
                        toast.show();
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Esercizio castingToTypeExe(Esercizio exe, Map<String, Object> argsMap) {
        Esercizio finalExe;

        if (StringUtils.equalsIgnoreCase(exe.getTipoEsercizio(), "Serie")) {
            finalExe = new EsercizioSerie(exe, ((EditText) argsMap.get("Ripetizioni")).getText().toString());
        } else if (StringUtils.equalsIgnoreCase(exe.getTipoEsercizio(), "Incrementale")) {
            finalExe = new EsercizioIncrementale(exe, ((EditText) argsMap.get("Inizio")).getText().toString(), ((EditText) argsMap.get("Picco")).getText().toString());
        } else if (StringUtils.equalsIgnoreCase(exe.getTipoEsercizio(), "Piramidale")) {
            finalExe = new EsercizioPiramidale(exe, ((EditText) argsMap.get("Inizio")).getText().toString(), ((EditText) argsMap.get("Picco")).getText().toString(), recoverList[((NumberPicker) argsMap.get("RecuperoSerie")).getValue()]);
        } else if (StringUtils.equalsIgnoreCase(exe.getTipoEsercizio(), "Tenuta")) {
            finalExe = new EsercizioTenuta(exe, ((EditText) argsMap.get("TempoEsecuzione")).getText().toString());
        } else {
            finalExe = null;
        }

        return finalExe;
    }

    public Map<String, Object> argsFromExe(String typeStr, View viewFragment) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.equalsIgnoreCase(typeStr, "Serie")) {
            addIfNotNull(map, "Ripetizioni", viewFragment.findViewById(R.id.textRipetizioni));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Incrementale")) {
            addIfNotNull(map, "Inizio", viewFragment.findViewById(R.id.repetitionStartText));
            addIfNotNull(map, "Picco", viewFragment.findViewById(R.id.peakText));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Piramidale")) {
            addIfNotNull(map, "Inizio", viewFragment.findViewById(R.id.repetitionStartText));
            addIfNotNull(map, "Picco", viewFragment.findViewById(R.id.peakText));
            addIfNotNull(map, "RecuperoSerie", viewFragment.findViewById(R.id.textRecoverSeries));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Tenuta")) {
            addIfNotNull(map, "TempoEsecuzione", viewFragment.findViewById(R.id.textExecutionTime));
        } else {
            Toast toast = Toast.makeText(context, "MAPPA VUOTA", duration);
            toast.show();
            return null;
        }
        return map;
    }

    private static void addIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }*/
}
