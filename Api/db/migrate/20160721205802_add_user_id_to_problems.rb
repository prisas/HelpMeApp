  class AddUserIdToProblems < ActiveRecord::Migration[5.0]
  def change
    add_reference :problems, :user, index: true, foreign_key: true
  end
end
