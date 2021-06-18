package com.davidechiarelli.taxcodeapi.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void successClean(){
        String original = "Gisele Bündchen da Conceição --> and remove | \" ! £ $ ? ^";
        String clean = Utils.cleanString(original);

        assertThat(clean)
                .isNotBlank()
                .isEqualTo("GiseleBundchendaConceicaoandremove");
    }
}
