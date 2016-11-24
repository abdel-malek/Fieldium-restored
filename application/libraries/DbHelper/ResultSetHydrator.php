<?php

namespace DbHelper;

/**
 * This class represents an object that is responsible for filling the ResultSet objects.
 *
 * @author Abd Almuhsen Allahham
 */
class ResultSetHydrator {

    /**
     *  This is the basic function where all the fill logic done.
     * 
     * @param resource $datebase_result_set the result set as returned from mysqli.
     * @param boolean $hydrate_data true if ww want to fill the rows, false otherwise.
     * @param int $start where to start from in the result set.
     * @param int $length number of rows to fill starting from $start.
     * @param int $hydration_type how should the rows represented (accociated array or numerical array).
     * @param boolean $hydrate_columns_names true if we want to fill result set columns names array, false otherwise.
     * @return \DbHelper\ResultSet the hydrated ResultSet.
     * 
     * @throws Exception this exception thorwn if there is an error in seeking config.
     */
    public function hydarate($datebase_result_set, $hydrate_data = true, $start = 0, $length = 0, $hydration_type = MYSQLI_NUM, $hydrate_columns_names = true) {
        if ($datebase_result_set === false) {
            return new \DbHelper\ResultSet(false);
        }
        $result_set = new \DbHelper\ResultSet(true);
        if ($hydrate_columns_names) {
            $columns = array();
            while ($field = $datebase_result_set->fetch_field()) {
                $columns[] = $field->name;
            }
            $result_set->setColumns($columns);
        }
        if ($hydrate_data) {
            if ($start > 0) {
                if (!$datebase_result_set->data_seek($start)) {
                    throw new Exception("Error in data seeking");
                }
            }
            if ($length <= 0) {
                $rows = array (); 
				while ($row = $datebase_result_set->fetch_assoc()) {
					  $rows[] = $row ; 
				}
                $result_set->setRows($rows);
            } else {
                $rows = array();
                $current_row = 0;
                while (($row = $datebase_result_set->fetch_array($hydration_type)) && $current_row < $length) {
                    $result_set->addRow($row);
                    $current_row++;
                }
            }
        }
        if ($datebase_result_set->num_rows != 0) {
            $result_set->setTotal($datebase_result_set->num_rows);
        }
        $datebase_result_set->free();
        return $result_set;
    }

}
