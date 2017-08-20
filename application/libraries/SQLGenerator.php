<?php

/**
 * This class is responsible for generating specific sql text.
 *
 * @author Abd Almuhsen Allahham
 * @author Osama Naoura
 */
class SQLGenerator {

    private $CI;
    private $db_types;
    private $parameters_directions;
    private $db_types_with_precision;
    private $db_types_fixed;

    public function __construct() {
        $this->CI = &get_instance();
//        $this->db_types = unserialize(DB_TYPES);
//        $this->parameters_directions = unserialize(PARAMETERS_DIRECTIONS);
//        $this->db_types_with_precision = unserialize(DB_TYPES_WITH_PRECISION);
//        $this->db_types_fixed = unserialize(DB_TYPES_FIXED_SIZE);
    }

    public function generateStoredProcedureCallStatement($sp_name, $parameters, $out_parameters) {
        $sql = $this->generateParametersAssignStatements($parameters);
        $sql .= "CALL `" . $sp_name . "`(";
		$i =0 ;
        foreach ($parameters as $param_name => $param) {
			$i ++ ; 
            $sql.="@" . $param_name;
            if ($i != count($parameters) ) {
                $sql.=",";
            }
			
        }
        $sql.=");" . PHP_EOL;

        if (!empty($out_parameters)) {
            $sql.=$this->_generateOutParametersSelect($sql, $parameters, $out_parameters);
        }
        return $sql;
    }

    public function generateStoredFunctionSelectStatement($fn_name, $parameters) {
        $sql = $this->generateParametersAssignStatements($parameters);
        $sql .= "SELECT `" . $fn_name . "`(";
        foreach ($parameters as $param_name => $param_props) {
            $sql.="@" . $param_name;
            if ($param_props !== end($parameters)) {
                $sql.=" , ";
            }
        }
        $sql.=");" . PHP_EOL;
        return $sql;
    }

    public function generateDbObjectDropStatement($db_object_name, $is_func = false) {
        $type = ($is_func) ? "FUNCTION" : "PROCEDURE";
        return "DROP " . $type . " IF EXISTS `" . $db_object_name . "`;";
    }

    public function generateParametersAssignStatements($parameters_values) {
        $sql = "";
        foreach ($parameters_values as $name => $value) {
            $sql .= "SET @" . $name . " = '".$value['value']."';" . PHP_EOL;
        }
        return $sql;
    }

    //************************************************************************//

    public function generateDbObjectExecuteQuery($type, $name, $parameters_values) {
        $sql = "";
        $t = 1;
        foreach ($parameters_values as $value) {
            $sql .= "SET @p" . $t . " = '" . $value . "';";
            $t++;
        }
        $sql .= PHP_EOL;

        if ($type == STORED_PROCEDURE_TYPE) {
            $sql .= "CALL `" . $name . "`(";
        } else {
            $sql .= "SELECT `" . $name . "`(";
        }

        $parameters_count = count($parameters_values);
        for ($t = 1; $t <= $parameters_count; $t++) {
            $sql .= "@p" . $t;
            if ($t < $parameters_count) {
                $sql .= " , ";
            }
        }
        $sql .= ");" . PHP_EOL;
        return $sql;
    }

    //************************************************************************//

    /**
     * 
     * 
     * @param int $type
     * @param string $name
     * @param array $parameters The array of parameters.
     * @param string $body The body code.
     * @param type $return_type
     * @param int $return_size
     * @param int $return_precision
     * 
     * @return type
     */
    public function generateDbObjectCreationQuery($type, $name, $parameters, $body, $return_type = null, $return_size = null, $return_precision = null) {
        $sql = $this->_formPrototype($type, $name, $return_type, $return_size, $return_precision, $parameters);
        $sql .= $this->_formBody($body);
        return $sql;
    }

    /**
     * Private Functions
     */
    private function _generateOutParametersSelect($sql, $parameters, $out_parameters) {
        $parameters_names = array_keys($parameters);
        $sql.="SELECT ";
        foreach ($out_parameters as $param_name) {
            $key_index = array_search($param_name, $parameters_names);
            if ($key_index === false) {
                die("'$param_name' not found in parameters to select it as an out parameter");
            }
            $key_index++;
            $sql.="@$param_name as `" . $param_name . "`";
            if ($param_name != end($out_parameters)) {
                $sql.=",";
            }
        }
        $sql.=";" . PHP_EOL;
        return $sql;
    }

    private function _formPrototype($type, $name, $return_type, $return_size, $return_precision, array $parameters) {
        $sql = "CREATE ";
        $is_procedure = true;
        if ($type == STORED_PROCEDURE_TYPE) {
            $sql .= "PROCEDURE ";
        } else {
            $sql .= "FUNCTION ";
            $is_procedure = false;
        }
        $sql .= "`" . $name . "`";
        $sql .= $this->_form_parameters($parameters, $is_procedure);
        if (!$is_procedure) {
            $sql .= $this->_form_return_type($return_type, $return_size, $return_precision);
        }
        return $sql;
    }

    private function _form_parameters(array $parameters, $is_procedure) {
        $sql = "( ";
        foreach ($parameters as $parameter) {
            if (is_object($parameter)) {
                $sql.=$this->_form_parameter_object($parameter, $is_procedure);
            } else if (is_array($parameter)) {
                $sql.=$this->_form_parameter_array($parameter, $is_procedure);
            }
            if ($parameter != end($parameters)) {
                $sql.=" , ";
            }
        }
        $sql .= ") ";
        return $sql;
    }

    private function _form_parameter_object(Entities\Parameter $parameter, $is_procedure) {
        $sql = "";
        if ($is_procedure) {

            $sql .= $this->parameters_directions[$parameter->getDirection()] . " ";
        }
        $sql .= "`" . $parameter->getName() . "` ";
        $type = array_search($parameter->getType(), $this->db_types);
        if (in_array($parameter->getType(), $this->db_types_with_precision)) {
            $type.="(" . $parameter->getSize() . "," . $parameter->getPrecision() . ")";
        } elseif (!in_array($parameter->getType(), $this->db_types_fixed)) {
            $type.="(" . $parameter->getSize() . ")";
        }
        $sql .= $type . " ";
        return $sql;
    }

    private function _form_parameter_array($parameter, $is_procedure) {
        $sql = "";
        if ($is_procedure) {
            $sql .= $this->parameters_directions[$parameter['direction']] . " ";
        }
        $sql .= "`" . $parameter['name'] . "` ";
        $type = $parameter['type'];
        if (in_array($parameter['type'], $this->db_types_with_precision)) {
            $type.="(" . $parameter['size'] . "," . $parameter->getPrecision() . ")";
        } elseif (!in_array($parameter['type'], $this->db_types_fixed)) {
            $type.="(" . $parameter['size'] . ")";
        }
        $sql .= $type . " ";
        return $sql;
    }

    private function _form_return_type($return_type, $return_size, $return_precision) {
        $type = $return_type;
        if (in_array($return_type, $this->db_types_with_precision)) {
            $type .= "(" . $return_size . "," . $return_precision . ")";
        } elseif (!in_array($return_type, $this->db_types_fixed)) {
            $type .= "(" . $return_size . ")";
        }
        $sql = $sql = "RETURNS " . $type . " ";
        return $sql;
    }

    private function _formBody($body) {
        $sql = "BEGIN \n";
        $sql.=$body;
        if (substr($sql, -1) != ';') {
            $sql.=";";
        }
        $sql.=" \n";
        $sql.="END;";
        return $sql;
    }

}
