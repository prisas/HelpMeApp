class ProblemsController < ApplicationController

  #before_action :authenticate_user!, only: [:create, :index]

  #GET /problems
  def index
    @problems = Problem.all
    problems_list = Problem.select do |problem|
      problem.timestamp != '1'
    end
    render :json => {
        message: problems_list,
    }.to_json, status: 200
  end

  #GET /problems/:id
  def show
    @problem = Problem.find(params[:id])
    response = {title => @problem.title, description => @problem.description,
                location => @problem.location, imageUri => @problem.imageUri}
    render :json => {
        message: response
    }.to_json, status: 200
  end

  #POST /problems
  def create
    #@problem = current_user.problems.new(problem_params)
    @problem = Problem.new(problem_params)

    if @problem.save
      render json: {
          message: 'Your problem has been successfully uploaded!'
      }.to_json, status: 200
    else
      render json: {
          message: 'There is a problem with the server, please try it again in a few moments!'
      }.to_json, status: 500
    end

  end

  private

  def problem_params
    params.require(:problem).permit(:title, :description)
  end

end
