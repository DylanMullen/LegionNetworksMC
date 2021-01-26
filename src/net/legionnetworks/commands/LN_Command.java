package net.legionnetworks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class LN_Command implements CommandExecutor
{

	protected String commandName;
	protected boolean staffCommand;

	protected CommandSender sender;
	protected String[] arguments;

	public LN_Command(String command, boolean staff)
	{
		this.commandName = command;
		this.staffCommand = staff;
	}

	protected abstract boolean run();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		this.sender = sender;
		this.arguments = args;
		return hasPermission() ? run() : showInvalidPermission();
	}
	
	protected boolean showInvalidPermission()
	{
		getSender().sendMessage("Error: Not enough permissions");
		return true;
	}

	protected boolean isConsole()
	{
		return !(this.sender instanceof Player);
	}

	protected boolean isPlayer()
	{
		return this.sender instanceof Player;
	}

	protected CommandSender getSender()
	{
		return this.sender;
	}

	protected Player getPlayer()
	{
		if (isPlayer())
			return (Player) this.sender;
		else
			return null;
	}

	protected boolean isStaff()
	{
		return staffCommand;
	}

	protected String getPermission()
	{
		return "legion." + (isStaff() ? "staff" : "user") + "." + commandName;
	}

	protected boolean hasPermission()
	{
		return getSender().hasPermission(getPermission());
	}

	protected String[] getArguments()
	{
		return arguments;
	}

}
