package com.revature.project1.DAO;

import com.revature.project1.Models.Employee;
import com.revature.project1.Models.Requests;
import com.revature.project1.Service.EmployeeService;
import com.revature.project1.Util.ConnectionFactory;
import com.revature.project1.Util.DTO.RequestSubmit;
import com.revature.project1.Util.Interface.Crudable;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

    public class RequestDAO implements Crudable<Requests>{

        @Override
        public Requests create(Requests newRequest) {

            try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

                String sql = "insert into requests (r_amount, r_type, r_requester) values (?, ?, ?)";
                // PreparedStatements prevent SQL injection
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // set the information for the ?
                //preparedStatement.setInt(1, newRequest.getRequestID());
                //preparedStatement.setString(2, newRequest.getRequestStatus());
                preparedStatement.setInt(1, newRequest.getRequestAmount());
                preparedStatement.setString(2, newRequest.getRequestType());
                preparedStatement.setString(3, newRequest.getRequestRequester());
                int checkInsert = preparedStatement.executeUpdate();

                if(checkInsert == 0) { throw new RuntimeException("Request was not submitted.");}
                else{ return newRequest; }

            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }
        public List<Requests> viewPreviousRequests(Employee employee) {
            List<Requests> previousRequests = new ArrayList<>();

            try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
                String sql = "select * from requests where r_requester = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, employee.getEmployeeUsername());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    previousRequests.add(convertSQLtoRequest(resultSet));
                }
                return previousRequests;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public RequestSubmit updateTicket(RequestSubmit update) {
            try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

                String sql = "update requests set r_status = " + update.getRequestStatus() + " where r_id =" + update.getRequestID();

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, update.getRequestStatus());
                preparedStatement.setInt(2, update.getRequestID());

                if (preparedStatement.executeUpdate() == 0) throw new SQLException("This request has not bee processed.");

                return update;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        public List<Requests> findAll() {
            return null;
        }

        @Override
        public Requests findByRequestID(int requestID) {
            return null;
        }

        /*public List<Requests> getPendingRequests(){
            List<Requests> requestList = new ArrayList<>();
            try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
                String sql = "select * from requests where r_status = 'pending'";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    requestList.add(convertSQLtoRequest());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return requestList;
        }*/

      /*  public List<Requests> findByStatus(Employee employee, Requests request){
            try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
                List<Requests> requestStatus = new LinkedList<>();

                String sql = "select * from requests where r_status = ? and r_requester = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, request.getRequestStatus());
                preparedStatement.setString(2, employee.getEmployeeUsername());


                ResultSet resultSet = preparedStatement.executeQuery();


                while(resultSet.next()){
                    requestStatus.add(convertSQLtoRequest(resultSet));
                };

                return requestStatus;

            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }

            }


        public List<Requests> findByApprovedStatus(Employee employee) {
            List<Requests> personalRequests = new ArrayList<>();

            try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
                String sql = "select * from request where r_requester = ? AND r_status = 'Approved'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, employee.getEmployeeUsername());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    personalRequests.add(convertSQLtoRequest(resultSet));
                }
                return personalRequests;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }


        public List<Requests> findByDeniedStatus(){
            try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
                List<Requests> deniedRequests = new LinkedList<>();

                String sql = " select * from requests join employee on employee.e_username = requests.r_requester where requests.status = 'false' order by requests.r_id ";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    deniedRequests.add(convertSQLtoRequest(resultSet));
                }

                return deniedRequests;

            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }

        }*/

        private Requests convertSQLtoRequest(ResultSet resultSet) throws SQLException{
            Requests request = new Requests();

            request.setRequestID(resultSet.getInt("r_id"));
            request.setRequestStatus(resultSet.getString("r_status"));
            request.setRequestAmount(resultSet.getInt("r_amount"));
            request.setRequestType(resultSet.getString("r_type"));
            request.setRequestRequester(resultSet.getString("r_requester"));

            return request;

        }

        @Override
        public boolean update(Requests updatedRequest) {
            return false;
        }

        @Override
        public boolean delete(int requestID) {
            return false;
        }

        @Override
        public Requests findByRequestType(Requests requestType) {
            return null;
        }

        @Override
        public Employee findByEmail(String employeeEmail){
            return null;
        }

        @Override
        public boolean delete(String employeeEmail){
            return false;
        }



        }

