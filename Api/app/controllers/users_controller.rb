class UsersController < ApplicationController

  def login
    #@user = User.new(params[:user])
    #email = @user.email
    #password = @user.password
    #user = User.where(email: email).take

    # unless user.nil?
    #   if user.password equal? password
    #     render status: 200
    #     return
    #   end
    # end
    # render status: 403

    render status: 200

  end

  def register
    name = params[:name]
    email = params[:email]
    password = params[:password]
      phone = params[:phone]

    @user = User.new(name: name, email: email, password: password, phone: phone)

    if @user.save
      render status: 200
    else
      render status: 500
    end

  end

end
