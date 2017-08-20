<?php

namespace DbHelper;

/**
 * This class represents a single query result set.
 *
 * @author Abd Almuhsen Allahham
 */
class ResultSet {

    private $has_data;
    private $error;
    private $columns;
    private $rows;
    private $total;

    /**
     * 
     * @param boolean $has_data true if the result set has data, false otherwise.
     */
    function __construct($has_data = true) {
        $this->has_data = $has_data;
        $this->total = 0;
    }

    /**
     * 
     * @return boolean true if the result set has data, false otherwise.
     */
    public function hasData() {
        return $this->has_data;
    }

    /**
     * 
     * @return string the error that occured in the result set.
     */
    public function getError() {
        return $this->error;
    }

    /**
     * 
     * @return array columns names array.
     */
    public function getColumns() {
        return $this->columns;
    }

    /**
     * 
     * @return array the rows of the result set.
     */
    public function getRows() {
        return $this->rows;
    }

    /**
     * 
     * @return int total number of rows
     */
    public function getTotal() {
        return $this->total;
    }

    /**
     * 
     * @param string $error the error occured in this result set.
     */
    public function setError($error) {
        $this->error = $error;
    }

    /**
     * 
     * @param array columns names array.
     */
    public function setColumns(array $columns) {
        $this->columns = $columns;
    }

    /**
     * 
     * @param array rows array.
     */
    public function setRows(array $rows) {
        $this->rows = $rows;
    }

    /**
     * 
     * @param int $total total number of rows.
     */
    public function setTotal($total) {
        $this->total = $total;
    }

    /**
     * adds row to the rows array.
     * @param array $row row values.
     */
    public function addRow(array $row) {
        if (empty($this->rows)) {
            $this->rows = array();
        }
        $this->rows[] = $row;
    }

    /**
     * check if the result set has an error.
     * @return boolean true if result set has an error, false otherwise.
     */
    public function hasError() {
        return !empty($this->error);
    }

}
