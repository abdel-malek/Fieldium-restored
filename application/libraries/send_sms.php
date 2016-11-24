<?php

class Send_sms extends CI_Controller {

    public function __construct() {
        // $this->load->database();
        //  $CI =& get_instance();
        //  $CI->load->model('DataSources/messages');
    }

    public function send_sms($message, $moblie, $lang) {
        $url = "";
        if ($lang == 1) {
            $message = $this->ToUnicode($message);
            $_url = 'http://sms.malath.net.sa/httpSmsProvider.aspx' . "?username=" . "KHALEEJSYS" . "&password=" . "0565610236" . "&mobile=" . $moblie . "&sender=" . 'KHALEEJ SYS' . "&unicode=U&message=" . $message . "";
        } else if ($lang == 2){
            $_url = 'http://sms.malath.net.sa/httpSmsProvider.aspx' . "?username=" . "KHALEEJSYS" . "&password=" . "0565610236" . "&mobile=" . $moblie . "&sender=" . 'KHALEEJ SYS' . "&unicode=E&message=" . $message . "";
        }
        $_url = preg_replace("/ /", "%20", $_url);
        $result = file_get_contents($_url);
        return $result;
    }

    private function ToUnicode($Text) {
        $Backslash = "\ ";
        $Backslash = trim($Backslash);
        $UniCode = Array
            (
            "،" => "060C",
            "؛" => "061B",
            "؟" => "061F",
            "ء" => "0621",
            "آ" => "0622",
            "أ" => "0623",
            "ؤ" => "0624",
            "إ" => "0625",
            "ئ" => "0626",
            "ا" => "0627",
            "ب" => "0628",
            "ة" => "0629",
            "ت" => "062A",
            "ث" => "062B",
            "ج" => "062C",
            "ح" => "062D",
            "خ" => "062E",
            "د" => "062F",
            "ذ" => "0630",
            "ر" => "0631",
            "ز" => "0632",
            "س" => "0633",
            "ش" => "0634",
            "ص" => "0635",
            "ض" => "0636",
            "ط" => "0637",
            "ظ" => "0638",
            "ع" => "0639",
            "غ" => "063A",
            "ف" => "0641",
            "ق" => "0642",
            "ك" => "0643",
            "ل" => "0644",
            "م" => "0645",
            "ن" => "0646",
            "ه" => "0647",
            "و" => "0648",
            "ى" => "0649",
            "ي" => "064A",
            "ـ" => "0640",
            "ً" => "064B",
            "ٌ" => "064C",
            "ٍ" => "064D",
            "َ" => "064E",
            "ُ" => "064F",
            "ِ" => "0650",
            "ّ" => "0651",
            "ْ" => "0652",
            "!" => "0021",
            '"' => "0022",
            "#" => "0023",
            "$" => "0024",
            "%" => "0025",
            "&" => "0026",
            "'" => "0027",
            "(" => "0028",
            ")" => "0029",
            "*" => "002A",
            "+" => "002B",
            "," => "002C",
            "-" => "002D",
            "." => "002E",
            "/" => "002F",
            "0" => "0030",
            "1" => "0031",
            "2" => "0032",
            "3" => "0033",
            "4" => "0034",
            "5" => "0035",
            "6" => "0036",
            "7" => "0037",
            "8" => "0038",
            "9" => "0039",
            ":" => "003A",
            ";" => "003B",
            "<" => "003C",
            "=" => "003D",
            ">" => "003E",
            "?" => "003F",
            "@" => "0040",
            "A" => "0041",
            "B" => "0042",
            "C" => "0043",
            "D" => "0044",
            "E" => "0045",
            "F" => "0046",
            "G" => "0047",
            "H" => "0048",
            "I" => "0049",
            "J" => "004A",
            "K" => "004B",
            "L" => "004C",
            "M" => "004D",
            "N" => "004E",
            "O" => "004F",
            "P" => "0050",
            "Q" => "0051",
            "R" => "0052",
            "S" => "0053",
            "T" => "0054",
            "U" => "0055",
            "V" => "0056",
            "W" => "0057",
            "X" => "0058",
            "Y" => "0059",
            "Z" => "005A",
            "[" => "005B",
            $Backslash => "005C",
            "]" => "005D",
            "^" => "005E",
            "_" => "005F",
            "`" => "0060",
            "a" => "0061",
            "b" => "0062",
            "c" => "0063",
            "d" => "0064",
            "e" => "0065",
            "f" => "0066",
            "g" => "0067",
            "h" => "0068",
            "i" => "0069",
            "j" => "006A",
            "k" => "006B",
            "l" => "006C",
            "m" => "006D",
            "n" => "006E",
            "o" => "006F",
            "p" => "0070",
            "q" => "0071",
            "r" => "0072",
            "s" => "0073",
            "t" => "0074",
            "u" => "0075",
            "v" => "0076",
            "w" => "0077",
            "x" => "0078",
            "y" => "0079",
            "z" => "007A",
            "{" => "007B",
            "|" => "007C",
            "}" => "007D",
            "~" => "007E",
            "©" => "00A9",
            "®" => "00AE",
            "÷" => "00F7",
            "×" => "00F7",
            "§" => "00A7",
            " " => "0020",
            "\n" => "000D",
            "\r" => "000A",
            "\t" => "0009",
            "é" => "00E9",
            "ç" => "00E7",
            "à" => "00E0",
            "ù" => "00F9",
            "µ" => "00B5",
            "è" => "00E8"
        );

        $Result = "";
        $StrLen = strlen($Text);
        //$myfile = fopen("newfile33.txt", "w") or die("Unable to open file!");
        //fwrite($myfile, $Text .'taher\n');
        //fwrite($myfile, $code .'taher\n');
        for ($i = 0; $i < $StrLen; $i++) {
            $currect_char = mb_substr($Text, $i, 1); // substr($Text,$i,1);
            if (array_key_exists($currect_char, $UniCode)) {
                $Result .= $UniCode[$currect_char];
            }
            //fwrite($myfile, $currect_char .' ');
            //fwrite($myfile, $UniCode[$currect_char]."\n");
        }
        //fclose($myfile);
        return $Result;
    }

}
