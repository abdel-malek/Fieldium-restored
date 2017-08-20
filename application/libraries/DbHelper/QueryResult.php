<?php

namespace DbHelper;

/**
 * This class represents the result of set of queries.
 *
 * @author Abd Almuhsen Allahham.
 * 
 */
class QueryResult {

    private $results;

    /**
     * Return the resultsets as array.
     * @return array an array of \DbHelper\ResultSet objects.
     */
    public function getResults() {
        return $this->results;
    }

    /**
     * Adds a result set to the result.
     * @param \DbHelper\ResultSet $result_set
     */
    public function addResultSet(ResultSet $result_set) {
        if (empty($this->results)) {
            $this->results = array();
        }
        $this->results[] = $result_set;
    }

    /**
     * checks if the results has an error.
     * @return boolean true if any result set has an error, false otherwise.
     */
    public function hasErrors() {
        $has_error = false;
        foreach ($this->results as $result) {
            if ($result->hasError()) {
                $has_error = true;
                break;
            }
        }
        return $has_error;
    }

    /**
     * returns errors array.
     * @return array reuslt sets errors.
     */
    public function getErrors() {
        $i = 0;
        $errors = array();
        foreach ($this->results as $result) {
            if (!$result->hasData()) {
                $errors[$i] = $result->getError();
            }
            $i++;
        }
        return $errors;
    }

    /**
     * get the first result set which has data.
     * @return \Dbhelper\ResultSet the result set, null if not found.
     */
    public function getFirstResultSet() {
        foreach ($this->results as $result) {
            if ($result->hasData()) {
                return $result;
            }
        }
        return null;
    }

    /**
     * get all result sets columns names.
     * @return array the indeces are the results set order, the values ar arrays of coulmns names.
     */
    public function getAllResultSetsColumns() {
        $i = -1;
        $columns = array();
        foreach ($this->results as $result) {
            $i++;
            if (!$result->hasData()) {
                continue;
            }
            $columns[$i] = $result->getColumns();
        }
        return $columns;
    }

}
