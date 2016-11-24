<?php

use DbHelper\Utils\ClassLoader;

/**
 * This class is the codeigniter object that handle DBHelper library.
 * This class handle querying databases outside codeigniter database (clients databases).
 *
 * @author a.lahham
 */
class DbHelper {

    private $CI;
    private $hydrator;

    function __construct() {

        require_once APPPATH . 'libraries/DbHelper/Utils/ClassLoader.php';

        //load FormTools classes
        $classLoader = new ClassLoader("DbHelper", APPPATH . "libraries");
        $classLoader->register();
        $this->CI = &get_instance();
        $this->hydrator = new DbHelper\ResultSetHydrator();
    }

    public function validateQuery($query, $db_name,$db_user,$password) {
        $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        if ($mysqli->multi_query(trim($query))) {
            do {
                $result = $mysqli->store_result();
                if ($result) {
                    $result->free();
                } else if (!empty($mysqli->error)) {
                    return $mysqli->error;
                }
                if ($mysqli->more_results()) {
                    $mysqli->next_result();
                } else {
                    break;
                }
            } while (true);
        } elseif (!empty($mysqli->error)) {
            return $mysqli->error;
        }
        return true;
    }

    /**
     * Executes block of queries on specific database.
     * @param string $query queries to execute.
     * @param int $db_id database id.
     * @param mixed $hydrate_options array of hydration options:<br/>
     * -it can be '' so that mean hydrate all resultset.<br/>
     * -it can be a number so that mean hydrate spicific resultset.<br/>
     * -it can be a numerical array so that mean hydrate the result set which thier number is in the array.<br/>
     * -it can be an array consists of four indecies:
     * <ul>
     * <li><b>hydrate_data:</b>true if we want to hydrate rows, false otherwise.</li>
     * <li><b>hydrate_columns_names:</b>true if we want to fill columns names, false otherwise.</li>
     * <li><b>start:</b>row to start hydrating from.</li>
     * <li><b>length:</b>number of rows to hydrate.</li>
     * <li><b>hydration_type:</b>type of hydration (associated array or numbered array)</li>
     * </ul>
     * -it can be an array of arrays with the previous format.
     * @param mysqli $mysqli the connection.
     * @return \DbHelper\QueryResult the queries result.
     * @throws Exception if error occured in the queries.
     */
    public function executeQuery($query, $db_name,$db_user,$password, $hydrate_options = "", $mysqli = null) {
        if ($mysqli === null) {
            $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        }
        if ($mysqli->multi_query(trim($query))) {
            if (is_numeric($hydrate_options)) { 
                return $this->_hydrateSpecificResultSet($mysqli, $hydrate_options);
            } elseif (is_array($hydrate_options)) { 
                return $this->_hydrateResultSetsWithOptions($mysqli, $hydrate_options);
            } elseif ($hydrate_options === '') {  
                return $this->_hydrateAllResultSets($mysqli);
            } else {
                throw new Exception("Unvalid hydartion options");
            }
        } elseif (!empty($mysqli->error)) {
            $result = new \DbHelper\QueryResult();
            $rs = new \DbHelper\ResultSet(false);
            $rs->setError($mysqli->error);
            $result->addResultSet($rs);
            return $result;
            //throw new Exception($mysqli->error);
        } else {
            throw new Exception("unknown error in DbHelper::executeQuery");
        }
    }

    /**
     * executes a stored procedure in specific database.
     * @param string $sp_name stored procedure name.
     * @param int $db_id database id.
     * @param array $parameters array of parameters (parameter_name => parameter_value).
     * @param array $out_params array of out parameters names.
     *  @param mixed $hydrate_options array of hydration options:<br/>
     * -it can be '' so that mean hydrate all resultset.<br/>
     * -it can be a number so that mean hydrate spicific resultset.<br/>
     * -it can be a numerical array so that mean hydrate the result set which thier number is in the array.<br/>
     * -it can be an array consists of four indecies:
     * <ul>
     * <li><b>hydrate_data:</b>true if we want to hydrate rows, false otherwise.</li>
     * <li><b>hydrate_columns_names:</b>true if we want to fill columns names, false otherwise.</li>
     * <li><b>start:</b>row to start hydrating from.</li>
     * <li><b>length:</b>number of rows to hydrate.</li>
     * <li><b>hydration_type:</b>type of hydration (associated array or numbered array)</li>
     * </ul>
     * -it can be an array of arrays with the previous format.
     * @return \DbHelper\QueryResult the stored procedure result.
     */
    public function executeStoredProcedure($sp_name, $db_name,$db_user,$password, array $parameters = array(), array $out_params = array(), $hydrate_options = "") {
        $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        if (!empty($parameters)) {
            $this->_executeParameters($parameters, $mysqli);
        } 
        $this->CI->load->library("SQLGenerator");
        $sql = $this->CI->sqlgenerator->generateStoredProcedureCallStatement($sp_name, $parameters, $out_params);
        return $this->executeQuery($sql, $db_name,$db_user,$password, $hydrate_options, $mysqli);
    }

    /**
     * executes a function in specific database.
     * @param string $fn_name stored function name.
     * @param int $db_id database id.
     * @param array $parameters array of parameters (parameter_name => parameter_value).
     * @param array $out_params array of out parameters names.
     * @param mixed $hydrate_options array of hydration options:<br/>
     * -it can be '' so that mean hydrate all resultset.<br/>
     * -it can be a number so that mean hydrate spicific resultset.<br/>
     * -it can be a numerical array so that mean hydrate the result set which thier number is in the array.<br/>
     * -it can be an array consists of four indecies:
     * <ul>
     * <li><b>hydrate_data:</b>true if we want to hydrate rows, false otherwise.</li>
     * <li><b>hydrate_columns_names:</b>true if we want to fill columns names, false otherwise.</li>
     * <li><b>start:</b>row to start hydrating from.</li>
     * <li><b>length:</b>number of rows to hydrate.</li>
     * <li><b>hydration_type:</b>type of hydration (associated array or numbered array)</li>
     * </ul>
     * -it can be an array of arrays with the previous format.
     * @return \DbHelper\QueryResult the stored procedure result.
     */
    public function executeStoredFunction($fn_name, $db_name,$db_user,$password, array $parameters = array(), $hydrate_options = "") {
        $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        if (!empty($parameters)) {
            $this->_executeParameters($parameters, $mysqli);
        }
        $this->CI->load->library("SQLGenerator");
        $sql = $this->CI->sqlgenerator->generateStoredFunctionSelectStatement($fn_name, $parameters);
        return $this->executeQuery($sql, $db_name,$db_user,$password, $hydrate_options, $mysqli);
    }

    /**
     * executes a report in specific database.
     * @param string $report_body report body.
     * @param int $db_id database id.
     * @param array $out_params array of out parameters names.
     * @param mixed $hydrate_options array of hydration options:<br/>
     * -it can be '' so that mean hydrate all resultset.<br/>
     * -it can be a number so that mean hydrate spicific resultset.<br/>
     * -it can be a numerical array so that mean hydrate the result set which thier number is in the array.<br/>
     * -it can be an array consists of four indecies:
     * <ul>
     * <li><b>hydrate_data:</b>true if we want to hydrate rows, false otherwise.</li>
     * <li><b>hydrate_columns_names:</b>true if we want to fill columns names, false otherwise.</li>
     * <li><b>start:</b>row to start hydrating from.</li>
     * <li><b>length:</b>number of rows to hydrate.</li>
     * <li><b>hydration_type:</b>type of hydration (associated array or numbered array)</li>
     * </ul>
     * -it can be an array of arrays with the previous format.
     * @return \DbHelper\QueryResult the report result.
     */
    public function executeReport($report_body, $db_name,$db_user,$password, array $parameters = array(), $hydrate_options = "") {
        $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        if (!empty($parameters)) {
            $this->_executeParameters($parameters, $mysqli);
        }
        return $query_result = $this->executeQuery($report_body, $db_name,$db_user,$password, $hydrate_options, $mysqli);
    }

    /**
     * 
     * Private functions
     */
    private function _connectToDatabase($db_name,$db_user,$password) {
        $local_db = $this->CI->load->database('template', TRUE, FALSE);
        $local_db->database = $db_name;
        $local_db->username = $db_user;
        $local_db->password = $password;
		
        if ($local_db->initialize()) {
            return $local_db->conn_id;
        }
    }

      private function _connectToInformationDatabase($db_name) {
        $local_db = $this->CI->load->database('template', TRUE, FALSE);
        $local_db->database = "information_schema";
        if ($local_db->initialize()) {
            return $local_db->conn_id;
        }
    }
    
    private function _hydrateSpecificResultSet($mysqli, $result_set_num) {
        $current_result_set = 0;
        do {
            $result = $mysqli->store_result();
            if ($current_result_set == $result_set_num) {
                $query_result = new \DbHelper\QueryResult();
                if ($result) {
                    $result_set = $this->hydrator->hydarate($result);
                    if (!$result_set->hasData() && !empty($mysqli->error)) {
                        $result_set->setError($mysqli->error);
                    }
                    $query_result->addResultSet($result_set);
                    //TODO: check if we must return QueryResult object
                    return $query_result;
                } else {
                    throw new Exception("invalid result set number");
                }
            } elseif ($result) {
                $result->free();
            }
            if (!$mysqli->more_results()) {
                break;
            }
            $mysqli->next_result();
            $current_result_set++;
        } while (true);
    }

    private function _hydrateResultSetsWithOptions($mysqli, $hydration_options) {

        if (array_key_exists("hydrate_data", $hydration_options) ||
                array_key_exists("hydrate_columns_names", $hydration_options) ||
                array_key_exists("start", $hydration_options) ||
                array_key_exists("length", $hydration_options) ||
                array_key_exists("hydration_type", $hydration_options)) {
            return $this->hydrateAllResultSetsWithGeneralOptions($mysqli, $hydration_options);
        }
        $hydration_options = $this->_prepareHydrationOptions($hydration_options);
        $current_result_set = 0;
        $query_result = new \DbHelper\QueryResult();
        do {
            $result = $mysqli->store_result();
            if (!isset($hydration_options[$current_result_set])) {
                if ($result) {
                    $result->free();
                }
            } else {
                $current_options = $hydration_options[$current_result_set];
                if (is_numeric($current_options)) {
                    $result_set = $this->hydrator->hydarate($result);
                    if (!$result_set->hasData() && !empty($mysqli->error)) {
                        $result_set->setError($mysqli->error);
                    }
                } else {
                    $hydrate_data = (isset($current_options["hydrate_data"])) ? (bool) $current_options["hydrate_data"] : true;
                    $start = (isset($current_options["start"])) ? (int) $current_options["start"] : 0;
                    $length = (isset($current_options["length"])) ? (int) $current_options["length"] : 0;
                    $hydration_type = (isset($current_options["hydration_type"])) ? (int) $current_options["hydration_type"] : MYSQL_NUM;
                    $hydrate_columns_names = (isset($current_options["hydrate_columns_names"])) ? (bool) $current_options["hydrate_columns_names"] : true;
                    $result_set = $this->hydrator->hydarate($result, $hydrate_data, $start, $length, $hydration_type, $hydrate_columns_names);
                    if (!$result_set->hasData() && !empty($mysqli->error)) {
                        $result_set->setError($mysqli->error);
                    }
                }
                $query_result->addResultSet($result_set);
            }
            if ($mysqli->more_results()) {
                $mysqli->next_result();
                $current_result_set++;
            } else {
                break;
            }
        } while (true);
        return $query_result;
    }

    private function _hydrateAllResultSets($mysqli) {
        $query_result = new \DbHelper\QueryResult();
        do {
            $result = $mysqli->store_result();
            $result_set = $this->hydrator->hydarate($result);
            if (!$result_set->hasData() && !empty($mysqli->error)) {
                $result_set->setError($mysqli->error);
            }
            $query_result->addResultSet($result_set);
            if ($mysqli->more_results()) {
                $mysqli->next_result();
            } else {
                break;
            }
        } while (true);
        return $query_result;
    }

    private function hydrateAllResultSetsWithGeneralOptions($mysqli, $hydration_options) {
        $query_result = new \DbHelper\QueryResult();
        $hydrate_data = (isset($hydration_options["hydrate_data"])) ? (bool) $hydration_options["hydrate_data"] : true;
        $start = (isset($hydration_options["start"])) ? (int) $hydration_options["start"] : 0;
        $length = (isset($hydration_options["length"])) ? (int) $hydration_options["length"] : 0;
        $hydration_type = (isset($hydration_options["hydration_type"])) ? (int) $hydration_options["hydration_type"] : MYSQL_NUM;
        $hydrate_columns_names = (isset($hydration_options["hydrate_columns_names"])) ? (bool) $hydration_options["hydrate_columns_names"] : true;
        do {
            $result = $mysqli->store_result();
            $result_set = $this->hydrator->hydarate($result, $hydrate_data, $start, $length, $hydration_type, $hydrate_columns_names);
            if (!$result_set->hasData() && !empty($mysqli->error)) {
                $result_set->setError($mysqli->error);
            }
            $query_result->addResultSet($result_set);
            if ($mysqli->more_results()) {
                $mysqli->next_result();
            } else {
                break;
            }
        } while (true);
        return $query_result;
    }

    private function _prepareHydrationOptions($hydration_options) {
        $hydration_options_indexes = array_keys($hydration_options);
        $numric_values = array_filter($hydration_options_indexes, "is_numeric");
        if ($numric_values !== $hydration_options_indexes) {
            throw new Exception("error in hydration options indexes");
        }
        $new_hydration_options = array();
        foreach ($hydration_options as $result_set_number => $temp_hydration_options) {
            if (is_array($temp_hydration_options)) {
                $new_hydration_options[$result_set_number] = $temp_hydration_options;
            } elseif (is_numeric($temp_hydration_options)) {
                $new_hydration_options[$temp_hydration_options] = $temp_hydration_options;
            } else {
                throw new Exception("error in hydration options values the value of index [" . $result_set_number . "] should be a number or an array");
            }
        }
        ksort($new_hydration_options);
        return $new_hydration_options;
    }

    private function _executeParameters(array $parameters, $mysqli) {
        foreach ($parameters as $param_name => $param_props) {
            $param_props_keys = array_keys($param_props);
            if (array_search("value", $param_props_keys) === false || array_search("type", $param_props_keys) === FALSE) {
                die("Error in param '$param_name'.<br/>Each parameter shold have an array with indecies 'value' and 'type' and parameter name sholud be the index of the parameter.");
            }
            $sql = "SET @" . $param_name . " = ?";
            $stmt = $mysqli->prepare($sql);
            $type = $param_props["type"];
            $t = $param_props["value"];
            $stmt->bind_param($type, $t);
            $res = $stmt->execute();
            if (!$res) {
                $stmt->close();
                throw new Exception($stmt->error);
            }
            $stmt->close();
        }
    }
    
    
    ///////////////////// MYSQL DUMP DATABASE ///////////////

    function mysqldump($db_name,$db_user,$password) {
        $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        $sql = "show tables;";
        $result = $mysqli->query($sql);
        if ($result) {
            while ($row = $result->fetch_row()) {
                
                $this->_mysqldump_table_structure($row[0], $mysqli);

                $this->_mysqldump_table_data($row[0], $mysqli);
            }
            $result->free();
        } else {
            echo "/* no tables in $mysql_database */\n";
        }
    }

    private function _mysqldump_table_structure($table, $mysqli) {
        echo "/* Table structure for table `$table` */\n";
        echo "DROP TABLE IF EXISTS `$table`;\n\n";
        $sql = "show create table `$table`; ";
        $result = $mysqli->query($sql);
        if ($result) {
            if ($row = $result->fetch_assoc()) {
                echo $row['Create Table'] . ";\n\n";
            }
        }
    }

    private function _mysqldump_table_data($table, $mysqli) {
        $sql = "select * from `$table`;";
        $result = $mysqli->query($sql);
        if ($result) {
            $num_rows = $result->num_rows;
            $num_fields = $result->field_count;
            if ($num_rows > 0) {
                echo "/* dumping data for table `$table` */\n";
                $field_type = array();
                $i = 0;
                while ($i < $num_fields) {
                    $meta = mysqli_fetch_field_direct($result, $i);
                    array_push($field_type, $meta->type);
                    $i++;
                }
                //print_r( $field_type);
                echo "insert into `$table` values\n";

                $index = 0;

                while ($row = $result->fetch_row()) {
                    echo "(";
                    for ($i = 0; $i < $num_fields; $i++) {
                        if (is_null($row[$i]))
                            echo "null";
                        else {
                            switch ($field_type[$i]) {
                                case 'int':
                                    echo $row[$i];
                                    break;

                                case 'string':
                                case 'blob' :
                                default:
                                    echo "'" . $mysqli->real_escape_string($row[$i]) . "'";
                            }
                        }
                        if ($i < $num_fields - 1)
                            echo ",";
                    }
                    echo ")";
                    if ($index < $num_rows - 1)
                        echo ",";
                    else
                        echo ";";

                    echo "\n";
                    $index++;
                }
            }
        }
        echo "\n";
    }
    
    public function get_all_procedure($db_name,$db_user,$password) {
          $mysqli = $this->_connectToDatabase($db_name,$db_user,$password);
        if (!$mysqli) {
            throw new ConnectionErrorException();  
        } else {
            $sql = "SHOW PROCEDURE STATUS WHERE db = '$db_name'";

            $result = mysqli_query($mysqli, $sql);
            $procedures = array();
            while ($r = mysqli_fetch_assoc($result)) {
                $procedures[] = $r;
            }
            $sql = "SHOW FUNCTION STATUS WHERE db = '$db_name'";

            $result = mysqli_query($mysqli, $sql);
            while ($r = mysqli_fetch_assoc($result)) {
                $procedures[] = $r;
            }
			
            return $procedures ; 
        }
    }
     public function get_procedure_params($db_name, $proc_name,$is_function,$db_user,$password) {

        $mysqli = $this->_connectToInformationDatabase($db_name);
        if (!$mysqli) {
            throw new ConnectionErrorException();  
        } else {
            $sql = "SELECT `PARAMETER_MODE`, `PARAMETER_NAME`, `DATA_TYPE` FROM `PARAMETERS` WHERE SPECIFIC_SCHEMA='" . $db_name . "' AND SPECIFIC_NAME='" . $proc_name . "';";
			
			
            $result = mysqli_query($mysqli, $sql);
            $j=0 ; 
            $params = array () ; 
            while ($row = $result->fetch_array()) {
				$params[$j]['direction'] = $row['PARAMETER_MODE'];  
				$params[$j]['name'] = $row['PARAMETER_NAME'];
				$params[$j]['type'] = $row['DATA_TYPE'];
				$j++ ; 
			}
           return $params;
        }
    }
    
    
   
    


}
